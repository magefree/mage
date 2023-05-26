
package mage.cards.g;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public final class GarnaTheBloodflame extends CardImpl {

    public GarnaTheBloodflame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Garna, the Bloodflame enters the battlefield, return to your hand all creature cards in your graveyard that were put there from anywhere this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GarnaTheBloodflameEffect(), false), new GarnaTheBloodflameWatcher());

        // Other creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, true)));
    }

    private GarnaTheBloodflame(final GarnaTheBloodflame card) {
        super(card);
    }

    @Override
    public GarnaTheBloodflame copy() {
        return new GarnaTheBloodflame(this);
    }
}

class GarnaTheBloodflameEffect extends OneShotEffect {

    GarnaTheBloodflameEffect() {
        super(Outcome.Benefit);
        staticText = "return to your hand all creature cards in your graveyard that were put there from anywhere this turn";
    }

    GarnaTheBloodflameEffect(final GarnaTheBloodflameEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            GarnaTheBloodflameWatcher watcher = game.getState().getWatcher(GarnaTheBloodflameWatcher.class);
            if (watcher != null) {
                Set<Card> toHand = new HashSet<>();
                for (UUID cardId : watcher.getCardsPutToGraveyardThisTurn()) {
                    Card card = game.getCard(cardId);
                    if (card != null && card.isOwnedBy(source.getControllerId()) && game.getState().getZone(cardId) == Zone.GRAVEYARD) {
                        toHand.add(card);
                    }
                }
                if (!toHand.isEmpty()) {
                    controller.moveCards(toHand, Zone.HAND, source, game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public GarnaTheBloodflameEffect copy() {
        return new GarnaTheBloodflameEffect(this);
    }
}

class GarnaTheBloodflameWatcher extends Watcher {

    private final Set<UUID> cards = new HashSet<>();

    public GarnaTheBloodflameWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.isCreature(game)) {
                cards.add(event.getTargetId());
            }
        }
    }

    public Set<UUID> getCardsPutToGraveyardThisTurn() {
        return cards;
    }

    @Override
    public void reset() {
        super.reset();
        cards.clear();
    }
}
