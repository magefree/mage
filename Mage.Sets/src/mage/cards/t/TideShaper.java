package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TideShaper extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.ISLAND, "An opponent controls an Island");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, false);
    private static final Hint hint = new ConditionHint(condition);

    public TideShaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kicker {1}
        this.addAbility(new KickerAbility("{1}"));

        // When Tide Shaper enters the battlefield, if it was kicked, target land becomes an Island for as long as Tide Shaper remains on the battlefield.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new TideShaperEffect()),
                KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, " +
                "target land becomes an Island for as long as {this} remains on the battlefield."
        );
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

        // Tide Shaper gets +1/+1 as long as an opponent controls an Island.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                condition, "{this} gets +1/+1 as long as an opponent controls an Island"
        )).addHint(hint));
    }

    private TideShaper(final TideShaper card) {
        super(card);
    }

    @Override
    public TideShaper copy() {
        return new TideShaper(this);
    }
}

class TideShaperEffect extends BecomesBasicLandTargetEffect {

    TideShaperEffect() {
        super(Duration.Custom, false, true, SubType.ISLAND);
    }

    private TideShaperEffect(final TideShaperEffect effect) {
        super(effect);
    }

    @Override
    public TideShaperEffect copy() {
        return new TideShaperEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        if (source.getSourcePermanentIfItStillExists(game) == null) {
            discard();
            return;
        }
        super.init(source, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getSourcePermanentIfItStillExists(game) == null) {
            discard();
            return false;
        }
        return super.apply(game, source);
    }
}
