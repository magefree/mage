package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.BloodToken;
import mage.game.permanent.token.InsectToken;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OldRutstein extends CardImpl {

    public OldRutstein(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Old Rutstein enters the battlefield or at the beginning of your upkeep, mill a card. If a land card is milled this way, create a Treasure token. If a creature card is milled this way, create a 1/1 green Insect creature token. If a noncreature, nonland card is milled this way, create a Blood token.
        this.addAbility(new OldRutsteinTriggeredAbility());
    }

    private OldRutstein(final OldRutstein card) {
        super(card);
    }

    @Override
    public OldRutstein copy() {
        return new OldRutstein(this);
    }
}

class OldRutsteinTriggeredAbility extends TriggeredAbilityImpl {

    OldRutsteinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new OldRutsteinEffect());
    }

    private OldRutsteinTriggeredAbility(final OldRutsteinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OldRutsteinTriggeredAbility copy() {
        return new OldRutsteinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return event.getTargetId().equals(getSourceId());
        }
        return game.isActivePlayer(getControllerId());
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield or at the beginning of your upkeep, mill a card. " +
                "If a land card is milled this way, create a Treasure token. " +
                "If a creature card is milled this way, create a 1/1 green Insect creature token. " +
                "If a noncreature, nonland card is milled this way, create a Blood token.";
    }
}

class OldRutsteinEffect extends OneShotEffect {

    OldRutsteinEffect() {
        super(Outcome.Benefit);
    }

    private OldRutsteinEffect(final OldRutsteinEffect effect) {
        super(effect);
    }

    @Override
    public OldRutsteinEffect copy() {
        return new OldRutsteinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(1, source, game);
        if (cards.getCards(game).stream().anyMatch(card -> card.isLand(game))) {
            new TreasureToken().putOntoBattlefield(1, game, source, source.getControllerId());
        }
        if (cards.getCards(game).stream().anyMatch(card -> card.isCreature(game))) {
            new InsectToken().putOntoBattlefield(1, game, source, source.getControllerId());
        }
        if (cards.getCards(game).stream().anyMatch(card -> !card.isCreature(game) && !card.isLand(game))) {
            new BloodToken().putOntoBattlefield(1, game, source, source.getControllerId());
        }
        return true;
    }
}
