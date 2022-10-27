package mage.cards.u;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnblinkingObserver extends CardImpl {

    public UnblinkingObserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Add {U}. Spend this mana only to pay a disturb cost or cast an instant or sorcery spell.
        this.addAbility(new ConditionalColoredManaAbility(
                new Mana(ManaType.BLUE, 1), new UnblinkingObserverManaBuilder()
        ));
    }

    private UnblinkingObserver(final UnblinkingObserver card) {
        super(card);
    }

    @Override
    public UnblinkingObserver copy() {
        return new UnblinkingObserver(this);
    }
}

class UnblinkingObserverManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new UnblinkingObserverConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to pay a disturb cost or cast an instant or sorcery spell.";
    }
}

class UnblinkingObserverConditionalMana extends ConditionalMana {

    UnblinkingObserverConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to pay a disturb cost or cast an instant or sorcery spell.";
        addCondition(new UnblinkingObserverManaCondition());
    }
}

class UnblinkingObserverManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof DisturbAbility) {
            return true;
        }
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source);
            return object != null && object.isInstantOrSorcery(game);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
