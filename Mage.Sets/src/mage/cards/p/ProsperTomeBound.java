package mage.cards.p;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.PlayCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProsperTomeBound extends CardImpl {

    public ProsperTomeBound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Mystic Arcanum — At the beginning of your end step, exile the top card of your library. Until the end of your next turn, you may play that card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(
                        1, false, Duration.UntilEndOfYourNextTurn
                ), TargetController.YOU, false
        ).withFlavorWord("Mystic Arcanum"));

        // Pact Boon — Whenever you play a card from exile, create a Treasure token.
        this.addAbility(new ProsperTomeBoundTriggeredAbility());
    }

    private ProsperTomeBound(final ProsperTomeBound card) {
        super(card);
    }

    @Override
    public ProsperTomeBound copy() {
        return new ProsperTomeBound(this);
    }
}

class ProsperTomeBoundTriggeredAbility extends PlayCardTriggeredAbility {

    ProsperTomeBoundTriggeredAbility() {
        super(TargetController.YOU, Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()));
        this.flavorWord = "Pact Boon";
        setTriggerPhrase("Whenever you play a card from exile, ");
    }

    private ProsperTomeBoundTriggeredAbility(final ProsperTomeBoundTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return super.checkTrigger(event, game) && event.getZone() == Zone.EXILED;
    }

    @Override
    public ProsperTomeBoundTriggeredAbility copy() {
        return new ProsperTomeBoundTriggeredAbility(this);
    }
}
