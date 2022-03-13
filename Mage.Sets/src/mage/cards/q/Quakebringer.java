package mage.cards.q;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Quakebringer extends CardImpl {

    public Quakebringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Your opponents can't gain life.
        this.addAbility(new SimpleStaticAbility(new CantGainLifeAllEffect(
                Duration.WhileOnBattlefield, TargetController.OPPONENT
        )));

        // At the beginning of your upkeep, Quakebringer deals 2 damage to each opponent. This ability triggers only if Quakebringer is on the battlefield or if Quakebringer is in your graveyard and you control a Giant.
        this.addAbility(new QuakebringerTriggeredAbility());

        // Foretell {2}{R}{R}
        this.addAbility(new ForetellAbility(this, "{2}{R}{R}"));
    }

    private Quakebringer(final Quakebringer card) {
        super(card);
    }

    @Override
    public Quakebringer copy() {
        return new Quakebringer(this);
    }
}

class QuakebringerTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GIANT);

    QuakebringerTriggeredAbility() {
        super(Zone.ALL, new DamagePlayersEffect(2, TargetController.OPPONENT));
    }

    private QuakebringerTriggeredAbility(final QuakebringerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (game.getState().getZone(getSourceId())) {
            case BATTLEFIELD:
                return game.isActivePlayer(getControllerId());
            case GRAVEYARD:
                return game.isActivePlayer(game.getOwnerId(getSourceId()))
                        && game.getBattlefield().count(filter, getControllerId(), this, game) > 0;
        }
        return false;
    }

    @Override
    public QuakebringerTriggeredAbility copy() {
        return new QuakebringerTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, {this} deals 2 damage to each opponent. " +
                "This ability triggers only if {this} is on the battlefield " +
                "or if {this} is in your graveyard and you control a Giant.";
    }
}