package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.RogueToken;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author weirddan455
 */
public final class MisfortuneTeller extends CardImpl {

    public MisfortuneTeller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Misfortune Teller enters the battlefield or deals combat damage to a player, exile target card from a graveyard.
        // If it was a creature card, create a 2/2 black Rogue creature token. If it was a land card, create a Treasure token. Otherwise, you gain 3 life.
        Ability ability = new MisfortuneTellerTriggeredAbility();
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private MisfortuneTeller(final MisfortuneTeller card) {
        super(card);
    }

    @Override
    public MisfortuneTeller copy() {
        return new MisfortuneTeller(this);
    }
}

class MisfortuneTellerEffect extends OneShotEffect {

    public MisfortuneTellerEffect() {
        super(Outcome.Exile);
        this.staticText = "exile target card from a graveyard. If it was a creature card, create a 2/2 black Rogue creature token. If it was a land card, create a Treasure token. Otherwise, you gain 3 life.";
    }

    private MisfortuneTellerEffect(final MisfortuneTellerEffect effect) {
        super(effect);
    }

    @Override
    public MisfortuneTellerEffect copy() {
        return new MisfortuneTellerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card == null) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        boolean creature = card.isCreature(game);
        boolean land = card.isLand(game);
        if (!controller.moveCards(card, Zone.EXILED, source, game)) {
            return false;
        }
        if (creature) {
            new RogueToken().putOntoBattlefield(1, game, source);
        }
        if (land) {
            new TreasureToken().putOntoBattlefield(1, game, source);
        }
        if (!creature && !land) {
            controller.gainLife(3, game, source);
        }
        return true;
    }
}

class MisfortuneTellerTriggeredAbility extends TriggeredAbilityImpl {

    public MisfortuneTellerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MisfortuneTellerEffect());
    }

    private MisfortuneTellerTriggeredAbility(final MisfortuneTellerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MisfortuneTellerTriggeredAbility copy() {
        return new MisfortuneTellerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return event.getTargetId().equals(sourceId);
        } else if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            return event.getSourceId().equals(sourceId) && ((DamagedEvent) event).isCombatDamage();
        } else {
            return false;
        }
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} enters the battlefield or deals combat damage to a player, ";
    }
}
