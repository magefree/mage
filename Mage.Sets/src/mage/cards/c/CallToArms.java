package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MostCommonColorCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CallToArms extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public CallToArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // As Call to Arms enters the battlefield, choose a color and an opponent.
        Ability ability = new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Detriment));
        ability.addEffect(new ChooseOpponentEffect(Outcome.Benefit).setText("and an opponent"));
        this.addAbility(ability);

        // White creatures get +1/+1 as long as the chosen color is the most common color among nontoken permanents the chosen player controls but isn't tied for most common.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false),
                CallToArmsCondition.instance, "white creatures get +1/+1 as long as the chosen color " +
                "is the most common color among nontoken permanents the chosen player controls but isn't tied for most common"
        )));

        // When the chosen color isn't the most common color among nontoken permanents the chosen player controls or is tied for most common, sacrifice Call to Arms.
        this.addAbility(new CallToArmsStateTriggeredAbility());
    }

    private CallToArms(final CallToArms card) {
        super(card);
    }

    @Override
    public CallToArms copy() {
        return new CallToArms(this);
    }
}

enum CallToArmsCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player opponent = game.getPlayer((UUID) game.getState().getValue(source.getSourceId() + ChooseOpponentEffect.VALUE_KEY));
        return permanent != null && opponent != null && new MostCommonColorCondition(
                (ObjectColor) game.getState().getValue(permanent.getId() + "_color"), true,
                Predicates.and(TokenPredicate.FALSE, new ControllerIdPredicate(opponent.getId()))
        ).apply(game, source);
    }
}

class CallToArmsStateTriggeredAbility extends StateTriggeredAbility {

    public CallToArmsStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
        setTriggerPhrase("When the chosen color isn't the most common color among nontoken permanents " +
                "the chosen player controls or is tied for most common, ");
    }

    private CallToArmsStateTriggeredAbility(final CallToArmsStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CallToArmsStateTriggeredAbility copy() {
        return new CallToArmsStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !CallToArmsCondition.instance.apply(game, this);
    }
}
