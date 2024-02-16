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
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new HiddenPredatorsToken(), null, Duration.Custom));
        this.replaceRuleText = false;
        setTriggerPhrase("When an opponent controls a creature with power 4 or greater, if {this} is an enchantment, ");
    }

    private HiddenPredatorsStateTriggeredAbility(final HiddenPredatorsStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HiddenPredatorsStateTriggeredAbility copy() {
        return new HiddenPredatorsStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !game.getBattlefield().getActivePermanents(filter, game.getControllerId(getSourceId()), game).isEmpty();
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        if (getSourcePermanentIfItStillExists(game) != null) {
            return getSourcePermanentIfItStillExists(game).isEnchantment(game);
        }
        return false;
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

    private HiddenPredatorsToken(final HiddenPredatorsToken token) {
        super(token);
    }

    public HiddenPredatorsToken copy() {
        return new HiddenPredatorsToken(this);
    }
}
