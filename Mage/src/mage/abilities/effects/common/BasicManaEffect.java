package mage.abilities.effects.common;

import mage.Constants;
import mage.Mana;
import mage.abilities.Ability;
import mage.game.Game;

public class BasicManaEffect extends ManaEffect<BasicManaEffect> {
    protected Mana mana;

    public BasicManaEffect(Mana mana) {
		super();
		this.mana = mana;
	}

    public BasicManaEffect(final BasicManaEffect effect) {
		super(effect);
		this.mana = effect.mana.copy();
	}

    @Override
    public BasicManaEffect copy() {
        return new BasicManaEffect(this);
    }

    @Override
	public boolean apply(Game game, Ability source) {
		game.getPlayer(source.getControllerId()).getManaPool().changeMana(mana);
		return true;
	}


	@Override
	public String getText(Ability source) {
		return "Add " + mana.toString() + " to your mana pool";
	}

	public Mana getMana() {
		return mana;
	}
}
