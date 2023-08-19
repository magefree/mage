package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.replacement.CreaturesAreExiledOnDeathReplacementEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;

/**
 *
 * @author weirddan455
 */
public final class LiesaForgottenArchangel extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public LiesaForgottenArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever another nontoken creature you control dies, return that card to its owner's hand at the beginning of the next end step.
        this.addAbility(new DiesCreatureTriggeredAbility(new LiesaForgottenArchangelReturnToHandEffect(), false, filter, true));

        // If a creature an opponent controls would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(
            new CreaturesAreExiledOnDeathReplacementEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE)
        ));
    }

    private LiesaForgottenArchangel(final LiesaForgottenArchangel card) {
        super(card);
    }

    @Override
    public LiesaForgottenArchangel copy() {
        return new LiesaForgottenArchangel(this);
    }
}

class LiesaForgottenArchangelReturnToHandEffect extends OneShotEffect {

    public LiesaForgottenArchangelReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return that card to its owner's hand at the beginning of the next end step";
    }

    private LiesaForgottenArchangelReturnToHandEffect(final LiesaForgottenArchangelReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public LiesaForgottenArchangelReturnToHandEffect copy() {
        return new LiesaForgottenArchangelReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("return that card to its owner's hand");
        effect.setTargetPointer(targetPointer);
        DelayedTriggeredAbility ability = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
        game.addDelayedTriggeredAbility(ability, source);
        return true;
    }
}
