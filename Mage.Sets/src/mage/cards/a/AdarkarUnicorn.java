package mage.cards.a;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class AdarkarUnicorn extends CardImpl {

    public AdarkarUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Add {U} or {1}{U}. Spend this mana only to pay cumulative upkeep costs.
        this.addAbility(new ConditionalColoredManaAbility(
                new Mana(0, 1, 0, 0, 0, 0, 0, 0), new AdarkarUnicornManaBuilder()));
        this.addAbility(new ConditionalColoredManaAbility(
                new Mana(0, 1, 0, 0, 0, 0, 0, 1), new AdarkarUnicornManaBuilder()));
    }

    private AdarkarUnicorn(final AdarkarUnicorn card) {
        super(card);
    }

    @Override
    public AdarkarUnicorn copy() {
        return new AdarkarUnicorn(this);
    }
}

class AdarkarUnicornManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new AdarkarUnicornConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to pay cumulative upkeep costs";
    }
}

class AdarkarUnicornConditionalMana extends ConditionalMana {

    public AdarkarUnicornConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to pay cumulative upkeep costs";
        addCondition(new AdarkarUnicornManaCondition());
    }
}

class AdarkarUnicornManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source != null) {
            return source instanceof CumulativeUpkeepAbility;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}