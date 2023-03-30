package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.common.PermanentWasCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Mistcaller extends CardImpl {

    public Mistcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Mistcaller: Until end of turn, if a nontoken creature would enter the battlefield and it wasn't cast, exile it instead.
        this.addAbility(new SimpleActivatedAbility(
                new ContainmentPriestReplacementEffect(),
                new SacrificeSourceCost()
        ), new PermanentWasCastWatcher());
    }

    private Mistcaller(final Mistcaller card) {
        super(card);
    }

    @Override
    public Mistcaller copy() {
        return new Mistcaller(this);
    }
}

class ContainmentPriestReplacementEffect extends ReplacementEffectImpl {

    public ContainmentPriestReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "until end of turn, if a nontoken creature would enter the battlefield and it wasn't cast, exile it instead";
    }

    public ContainmentPriestReplacementEffect(final ContainmentPriestReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ContainmentPriestReplacementEffect copy() {
        return new ContainmentPriestReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                controller.moveCardsToExile(card, source, game, true, null, null);
            }
            return true;

        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE; // Token create the create Token event
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.BATTLEFIELD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                Object entersTransformed = game.getState().getValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + event.getTargetId());
                if (entersTransformed instanceof Boolean && (Boolean) entersTransformed && card.getSecondCardFace() != null) {
                    card = card.getSecondCardFace();
                }
                if (card != null && card.isCreature(game)) { // TODO: Bestow Card cast as Enchantment probably not handled correctly
                    PermanentWasCastWatcher watcher = game.getState().getWatcher(PermanentWasCastWatcher.class);
                    return watcher != null && !watcher.wasPermanentCastThisTurn(event.getTargetId());
                }
            }
        }
        return false;
    }
}
