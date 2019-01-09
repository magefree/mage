package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirebladeArtist extends CardImpl {

    public FirebladeArtist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of your upkeep, you may sacrifice a creature. When you do, Fireblade Artist deals 2 damage to target opponent or planeswalker.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoIfCostPaid(
                        new FirebladeArtistCreateReflexiveTriggerEffect(),
                        new SacrificeTargetCost(new TargetControlledPermanent(
                                StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
                        )), "Sacrifice a creature to deal 2 damage to an opponent or planeswalker?"
                ).setText("you may sacrifice a creature. When you do, " +
                        "{this} deals 2 damage to target opponent or planeswalker."),
                TargetController.YOU, false
        ));
    }

    private FirebladeArtist(final FirebladeArtist card) {
        super(card);
    }

    @Override
    public FirebladeArtist copy() {
        return new FirebladeArtist(this);
    }
}

class FirebladeArtistCreateReflexiveTriggerEffect extends OneShotEffect {

    FirebladeArtistCreateReflexiveTriggerEffect() {
        super(Outcome.Benefit);
    }

    private FirebladeArtistCreateReflexiveTriggerEffect(final FirebladeArtistCreateReflexiveTriggerEffect effect) {
        super(effect);
    }

    @Override
    public FirebladeArtistCreateReflexiveTriggerEffect copy() {
        return new FirebladeArtistCreateReflexiveTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new FirebladeArtistReflexiveTriggeredAbility(), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class FirebladeArtistReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    FirebladeArtistReflexiveTriggeredAbility() {
        super(new DamageTargetEffect(2), Duration.OneUse, true);
        this.addTarget(new TargetOpponentOrPlaneswalker());
    }

    private FirebladeArtistReflexiveTriggeredAbility(final FirebladeArtistReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FirebladeArtistReflexiveTriggeredAbility copy() {
        return new FirebladeArtistReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "{this} deals 2 damage to target opponent or planeswalker.";
    }
}
