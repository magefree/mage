package mage.cards.d;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DokuchiSilencer extends CardImpl {

    public DokuchiSilencer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Ninjutsu {1}{B}
        this.addAbility(new NinjutsuAbility("{1}{B}"));

        // Whenever Dokuchi Silencer deals combat damage to a player, you may discard a creature card. When you do, destroy target creature or planeswalker that player controls.
        this.addAbility(new BlindZealotTriggeredAbility());
    }

    private DokuchiSilencer(final DokuchiSilencer card) {
        super(card);
    }

    @Override
    public DokuchiSilencer copy() {
        return new DokuchiSilencer(this);
    }
}

class BlindZealotTriggeredAbility extends TriggeredAbilityImpl {

    BlindZealotTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private BlindZealotTriggeredAbility(final BlindZealotTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BlindZealotTriggeredAbility copy() {
        return new BlindZealotTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent == null
                || !event.getSourceId().equals(getSourceId())
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent(
                "creature or planeswalker" + opponent.getLogName() + " controls"
        );
        filter.add(new ControllerIdPredicate(opponent.getId()));
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DestroyTargetEffect(), false,
                "destroy target creature or planeswalker that player controls"
        );
        this.getEffects().clear();
        this.addEffect(new DoWhenCostPaid(
                ability,
                new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE)),
                "Discard a creature card?"
        ));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may discard a creature card. " +
                "When you do, destroy target creature or planeswalker that player controls.";
    }
}
