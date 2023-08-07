package mage.cards.g;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreatHallOfTheCitadel extends CardImpl {

    public GreatHallOfTheCitadel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Add two mana in any combination of colors. Spend this mana only to cast legendary spells.
        Ability ability = new ConditionalAnyColorManaAbility(
                new GenericManaCost(1), 2, new GreatHallOfTheCitadelManaBuilder()
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private GreatHallOfTheCitadel(final GreatHallOfTheCitadel card) {
        super(card);
    }

    @Override
    public GreatHallOfTheCitadel copy() {
        return new GreatHallOfTheCitadel(this);
    }
}

class GreatHallOfTheCitadelManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new GreatHallOfTheCitadelConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast legendary spells";
    }
}

class GreatHallOfTheCitadelConditionalMana extends ConditionalMana {

    public GreatHallOfTheCitadelConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast legendary spells";
        addCondition(new GreatHallOfTheCitadelManaCondition());
    }
}

class GreatHallOfTheCitadelManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source);
            return object != null && object.isLegendary(game);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
