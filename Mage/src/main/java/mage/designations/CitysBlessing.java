package mage.designations;

/**
 * @author LevelX2
 */
public class CitysBlessing extends Designation {

    public CitysBlessing() {
        super(DesignationType.CITYS_BLESSING);
    }

    private CitysBlessing(final CitysBlessing card) {
        super(card);
    }

    @Override
    public CitysBlessing copy() {
        return new CitysBlessing(this);
    }
}
