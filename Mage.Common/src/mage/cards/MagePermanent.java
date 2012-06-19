package mage.cards;

import java.util.List;

import mage.view.PermanentView;

public abstract class MagePermanent extends MageCard {
    private static final long serialVersionUID = -3469258620601702171L;
    abstract public List<MagePermanent> getLinks();
    abstract public void update(PermanentView card);
    abstract public PermanentView getOriginalPermanent();
}
