package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author jeffwadsworth
 */
public final class HiddenPredators extends CardImpl {

    public HiddenPredators(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // When an opponent controls a creature with power 4 or greater, if Hidden Predators is an enchantment, Hidden Predators becomes a 4/4 Beast creature.
        this.addAbility(new HiddenPredatorsStateTriggeredAbility());
    }

    public HiddenPredators(final HiddenPredators card) {
        super(card);
    }

    @Override
    public HiddenPredators copy() {
        return new HiddenPredators(this);
    }
}

class HiddenPredatorsStateTriggeredAbility extends StateTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public HiddenPredatorsStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new HiddenPredatorsToken(), "", Duration.Custom, true, false));
    }

    public HiddenPredatorsStateTriggeredAbility(final HiddenPredatorsStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HiddenPredatorsStateTriggeredAbility copy() {
        return new HiddenPredatorsStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !game.getBattlefield().getAllActivePermanents(filter, game).isEmpty();
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        if (getSourcePermanentIfItStillExists(game) != null) {
            return getSourcePermanentIfItStillExists(game).isEnchantment();
        }
        return false;
    }

    @Override
    public boolean canTrigger(Game game) {
        //20100716 - 603.8
        Boolean triggered = (Boolean) game.getState().getValue(getSourceId().toString() + "triggered");
        if (triggered == null) {
            triggered = Boolean.FALSE;
        }
        return !triggered;
    }

    @Override
    public void trigger(Game game, UUID controllerId) {
        //20100716 - 603.8
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.TRUE);
        super.trigger(game, controllerId);
    }

    @Override
    public boolean resolve(Game game) {
        //20100716 - 603.8
        boolean result = super.resolve(game);
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.FALSE);
        return result;
    }

    @Override
    public void counter(Game game) {
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.FALSE);
    }

    @Override
    public String getRule() {
        return "When an opponent controls a creature with 4 or greater power, if {this} is an enchantment, " + super.getRule();
    }

}

class HiddenPredatorsToken extends TokenImpl {

    public HiddenPredatorsToken() {
        super("Beast", "4/4 Beast creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.BEAST);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public HiddenPredatorsToken(final HiddenPredatorsToken token) {
        super(token);
    }

    public HiddenPredatorsToken copy() {
        return new HiddenPredatorsToken(this);
    }
}
