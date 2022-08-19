package mage.cards.h;

import mage.MageInt;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HiddenPredators extends CardImpl {

    public HiddenPredators(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // When an opponent controls a creature with power 4 or greater, if Hidden Predators is an enchantment, Hidden Predators becomes a 4/4 Beast creature.
        this.addAbility(new HiddenPredatorsStateTriggeredAbility());
    }

    private HiddenPredators(final HiddenPredators card) {
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
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public HiddenPredatorsStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new HiddenPredatorsToken(), "", Duration.Custom, true, false));
        setTriggerPhrase("When an opponent controls a creature with 4 or greater power, if {this} is an enchantment, ");
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
            return getSourcePermanentIfItStillExists(game).isEnchantment(game);
        }
        return false;
    }

    @Override
    public boolean canTrigger(Game game) {
        //20100716 - 603.8
        return !Boolean.TRUE.equals(game.getState().getValue(getSourceId().toString() + "triggered"));
    }

    @Override
    public void trigger(Game game, UUID controllerId, GameEvent triggeringEvent) {
        //20100716 - 603.8
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.TRUE);
        super.trigger(game, controllerId, triggeringEvent);
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
