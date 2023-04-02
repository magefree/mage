package mage.cards.o;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OmenHawker extends CardImpl {

    public OmenHawker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.CEPHALID);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add {C}{U}. Spend this many only to activate abilities.
        this.addAbility(new ConditionalColoredManaAbility(
                new Mana(0, 1, 0, 0, 0, 0, 0, 1), new OmenHawkerManaBuilder()
        ));
    }

    private OmenHawker(final OmenHawker card) {
        super(card);
    }

    @Override
    public OmenHawker copy() {
        return new OmenHawker(this);
    }
}

class OmenHawkerManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new OmenHawkerConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate abilities";
    }
}

class OmenHawkerConditionalMana extends ConditionalMana {

    OmenHawkerConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to activate abilities";
        addCondition(new OmenHawkerManaCondition());
    }
}

class OmenHawkerManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source != null) {
            return source.getAbilityType() == AbilityType.MANA
                    || source.getAbilityType() == AbilityType.ACTIVATED;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
