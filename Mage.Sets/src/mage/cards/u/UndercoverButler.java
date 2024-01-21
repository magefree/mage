package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.other.PlayerWithTheMostLifePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * Undercover Butler {2}{U/B}
 * Creature - Human Rogue 2/3
 * Whenever Undercover Butler attacks the player with the most life or tied for most life, it can't be blocked this turn.
 *
 * @author DominionSpy
 */
public final class UndercoverButler extends CardImpl {

    public UndercoverButler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Undercover Butler attacks the player with the most life or tied for most life, it can't be blocked this turn.
        this.addAbility(new UndercoverButlerAbility().withRuleTextReplacement(true));
    }

    private UndercoverButler(final UndercoverButler card) {
        super(card);
    }

    @Override
    public UndercoverButler copy() {
        return new UndercoverButler(this);
    }
}

class UndercoverButlerAbility extends TriggeredAbilityImpl {

    UndercoverButlerAbility() {
        super(Zone.BATTLEFIELD, new CantBeBlockedSourceEffect(Duration.EndOfTurn), false);
        setTriggerPhrase("Whenever {this} attacks the player with the most life or tied for the most life, ");
    }

    private UndercoverButlerAbility(final UndercoverButlerAbility ability) {
        super(ability);
    }

    @Override
    public UndercoverButlerAbility copy() {
        return new UndercoverButlerAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID defenderId = game.getCombat().getDefenderId(getSourceId());
        if (defenderId == null) {
            return false;
        }

        Player attackedPlayer = game.getPlayer(defenderId);
        return PlayerWithTheMostLifePredicate.instance.apply(
                new ObjectSourcePlayer<>(attackedPlayer, getControllerId(), null),
                game
        );
    }
}
