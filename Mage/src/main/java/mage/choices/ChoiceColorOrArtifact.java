package mage.choices;

public class ChoiceColorOrArtifact extends ChoiceColor {
    public ChoiceColorOrArtifact() {
        this.choices.add("Artifacts");
        this.message = "Choose protection from";
    }

    public ChoiceColorOrArtifact(final ChoiceColorOrArtifact choice) {
        super(choice);
    }

    @Override
    public ChoiceColorOrArtifact copy() {
        return new ChoiceColorOrArtifact(this);
    }

    public boolean isArtifactSelected() {
        return choice.equals("Artifacts");
    }
}
