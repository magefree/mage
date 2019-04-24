package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.common.CreatureWasCastWatcher;

/**
 *
 * @author LevelX2
 */
public final class ContainmentPriest extends CardImpl {

    public ContainmentPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // If a nontoken creature would enter the battlefield and it wasn't cast, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ContainmentPriestReplacementEffect()), new CreatureWasCastWatcher());
    }

    public ContainmentPriest(final ContainmentPriest card) {
        super(card);
    }

    @Override
    public ContainmentPriest copy() {
        return new ContainmentPriest(this);
    }
}

class ContainmentPriestReplacementEffect extends ReplacementEffectImpl {

    public ContainmentPriestReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a nontoken creature would enter the battlefield and it wasn't cast, exile it instead";
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
            Object entersTransformed = game.getState().getValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + event.getTargetId());
            if (entersTransformed instanceof Boolean && (Boolean) entersTransformed && card.getSecondCardFace() != null) {
                card = card.getSecondCardFace();
            }
            if (card.isCreature()) { // TODO: Bestow Card cast as Enchantment probably not handled correctly
                CreatureWasCastWatcher watcher = (CreatureWasCastWatcher) game.getState().getWatchers().get(CreatureWasCastWatcher.class.getSimpleName());
                if (watcher != null && !watcher.wasCreatureCastThisTurn(event.getTargetId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
