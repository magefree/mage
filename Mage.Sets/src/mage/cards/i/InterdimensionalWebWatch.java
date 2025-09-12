package mage.cards.i;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class InterdimensionalWebWatch extends CardImpl {

    public InterdimensionalWebWatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        

        // When this artifact enters, exile the top two cards of your library. Until the end of your next turn, you may play those cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileTopXMayPlayUntilEffect(2, Duration.UntilEndOfYourNextTurn)));

        // {T}: Add two mana in any combination of colors. Spend this mana only to cast spells from exile.
        this.addAbility(new ConditionalAnyColorManaAbility(2, new InterdimensionalWebWatchManaBuilder()));
    }

    private InterdimensionalWebWatch(final InterdimensionalWebWatch card) {
        super(card);
    }

    @Override
    public InterdimensionalWebWatch copy() {
        return new InterdimensionalWebWatch(this);
    }
}

class InterdimensionalWebWatchManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new InterdimensionalWebWatchConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast spells from exile";
    }
}

class InterdimensionalWebWatchConditionalMana extends ConditionalMana {

    public InterdimensionalWebWatchConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast spells from exile";
        addCondition(new InterdimensionalWebWatchCondition());
    }
}

class InterdimensionalWebWatchCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        if (game.inCheckPlayableState()) {
            return object instanceof Card && game.getState().getZone(source.getSourceId()) == Zone.EXILED;
        }
        return object instanceof Spell && ((Spell) object).getFromZone() == Zone.EXILED;
    }
}
