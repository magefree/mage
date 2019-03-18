package mage.abilities.effects.mana;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
import mage.game.Game;

public class BasicManaEffect extends ManaEffect {

    protected Mana manaTemplate;

    public BasicManaEffect(Mana mana) {
        super();
        this.manaTemplate = mana;
        staticText = "add " + mana.toString();
    }

    public BasicManaEffect(ConditionalMana conditionalMana) {
        super();
        this.manaTemplate = conditionalMana;
        staticText = "add " + manaTemplate.toString() + " " + conditionalMana.getDescription();
    }

    public BasicManaEffect(final BasicManaEffect effect) {
        super(effect);
        this.manaTemplate = effect.manaTemplate.copy();

    }

    @Override
    public BasicManaEffect copy() {
        return new BasicManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getPlayer(source.getControllerId()).getManaPool().addMana(getMana(game, source), game, source);
        return true;
    }

    public Mana getManaTemplate() {
        return manaTemplate;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        return manaTemplate.copy();
    }

}
