package mage.cards;

import mage.view.PermanentView;

public abstract class MagePermanent extends MageCard {

    private static final long serialVersionUID = -3469258620601702171L;

    public abstract void update(PermanentView card);

    public abstract PermanentView getOriginalPermanent();

    public boolean isCreature() {
        return getOriginal().isCreature();
    }

    public boolean isLand() {
        return getOriginal().isLand();
    }

}
