package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RestlessBloodseeker extends TransformingDoubleFacedCard {

    private static final Condition condition = new YouGainedLifeCondition();
    private static final Hint hint = new ConditionHint(condition, "You gained life this turn");
    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.BLOOD, "Blood tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public RestlessBloodseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE}, "{1}{B}",
                "Bloodsoaked Reveler",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE}, "B"
        );

        // Restless Bloodseeker
        this.getLeftHalfCard().setPT(1, 3);

        // At the beginning of your end step, if you gained life this turn, create a Blood token.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new CreateTokenEffect(new BloodToken()),
                false, condition
        ).addHint(hint));

        // Sacrifice two Blood tokens: Transform Restless Bloodseeker. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
                new TransformSourceEffect(),
                new SacrificeTargetCost(2, filter)
        ));

        // Bloodsoaked Reveler
        this.getRightHalfCard().setPT(3, 3);

        // At the beginning of your end step, if you gained life this turn, create a Blood token.
        this.getRightHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new CreateTokenEffect(new BloodToken()),
                false, condition
        ).addHint(hint));

        // {4}{B}: Each opponent loses 2 life and you gain 2 life.
        Ability ability = new SimpleActivatedAbility(
                new LoseLifeOpponentsEffect(2), new ManaCostsImpl<>("{4}{B}")
        );
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.getRightHalfCard().addAbility(ability);
    }

    private RestlessBloodseeker(final RestlessBloodseeker card) {
        super(card);
    }

    @Override
    public RestlessBloodseeker copy() {
        return new RestlessBloodseeker(this);
    }
}
