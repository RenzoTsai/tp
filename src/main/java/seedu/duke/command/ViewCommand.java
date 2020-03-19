package seedu.duke.command;

import seedu.duke.data.AvailableModulesList;
import seedu.duke.data.SelectedModulesList;
import seedu.duke.data.SemModulesList;
import seedu.duke.module.Module;
import seedu.duke.ui.Ui;

public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String VIEW_COMPULSORY_MODULES = "cm";
    public static final String VIEW_DONE_MODULES = "dm";
    public static final String VIEW_MODULE_PLAN = "mp";
    public static final String VIEW_SPECIFIC_MODULE = "sm";
    public static final String VIEW_AVAILABLE_MODULES = "am";
    private String viewTaskType;
    private String moduleToBeViewed;

    public ViewCommand(String viewTaskType) {
        this.viewTaskType = viewTaskType;
    }

    public ViewCommand(String viewTaskType, String moduleIdentifier) {
        this.viewTaskType = viewTaskType;
        this.moduleToBeViewed = moduleIdentifier;
    }

    @Override
    public void execute(SelectedModulesList selectedModulesList, AvailableModulesList availableModulesList) {
        switch (viewTaskType) {
        case VIEW_MODULE_PLAN:
            viewModulePlan(selectedModulesList);
            break;
        case VIEW_DONE_MODULES:
            viewDoneModules(selectedModulesList);
            break;
        case VIEW_AVAILABLE_MODULES:
            viewAvailableModules(availableModulesList);
            break;
        default:
            return;
        }
    }

    /**
     * Prints the user's module plan.
     * @param moduleList user's module list.
     */
    private void viewModulePlan(SelectedModulesList moduleList) {
        StringBuilder viewList = new StringBuilder();
        for (SemModulesList sem: moduleList) {
            viewList.append(sem.getSem()).append(System.lineSeparator());
            for (Module selectedModule: sem) {
                int index = sem.indexOf(selectedModule) + 1;
                viewList.append(index).append(".")
                        .append(selectedModule.toString())
                        .append(System.lineSeparator());
            }
            viewList.append(System.lineSeparator());
        }
        Ui.showViewMessage(viewList.toString().trim());
    }

    private void viewDoneModules(SelectedModulesList moduleList) {
        StringBuilder viewList = new StringBuilder();
        for (SemModulesList sem: moduleList) {
            viewList.append(sem.getSem()).append(System.lineSeparator());
            for (Module selectedModule: sem) {
                int index = sem.indexOf(selectedModule) + 1;
                if (selectedModule.getDone()) {
                    viewList.append(index).append(".")
                            .append(selectedModule.toString())
                            .append(System.lineSeparator());

                }
            }
            viewList.append(System.lineSeparator());
        }
        Ui.showViewDoneMessage(viewList.toString().trim());
    }

    private void viewAvailableModules(AvailableModulesList modulesList) {
        StringBuilder viewList = new StringBuilder();
        for (Module module : modulesList) {
            int index = modulesList.indexOf(module) + 1;
            viewList.append(Ui.BOX_MARGIN)
                    .append(System.lineSeparator())
                    .append("|  ").append(String.format("%02d", index)).append(" | ");

            viewList.append(module.getId());
            alignId(viewList, module);

            viewList.append(module.getName());
            alignName(viewList, module);

            viewList.append(module.getPreReqModulesID());
            alignPreReqModules(viewList, module);
        }

        viewList.append(System.lineSeparator());
        Ui.showViewAvailableMessage(viewList.toString().trim());
    }

    private void alignPreReqModules(StringBuilder viewList, Module module) {
        int lengthOfPreReqModulesColumn = 36;
        viewList.append(" ".repeat(Math.max(0,
                (lengthOfPreReqModulesColumn - module.getPreReqModulesID().length()))));
        viewList.append("|").append(System.lineSeparator());
    }

    private void alignName(StringBuilder viewList, Module module) {
        int lengthOfNameColumn = 62;
        viewList.append(" ".repeat(Math.max(0,
                (lengthOfNameColumn - module.getName().length()))));
        viewList.append("| ");
    }

    private void alignId(StringBuilder viewList, Module module) {
        int lengthOfIDsColumn = 9;
        viewList.append(" ".repeat(Math.max(0,
                (lengthOfIDsColumn - module.getId().length()))));
        viewList.append("| ");
    }
}
