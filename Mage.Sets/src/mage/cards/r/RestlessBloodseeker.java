package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.BloodToken;
import mage.target.common.TargetControlledPermanent;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RestlessBloodseeker extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 0);
    private static final Hint hint = new ConditionHint(condition, "You gained life this turn");
    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.BLOOD, "Blood tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public RestlessBloodseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.b.BloodsoakedReveler.class;

        // At the beginning of your end step, if you gained life this turn, create a Blood token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new BloodToken()),
                TargetController.YOU, condition, false
        ).addHint(hint), new PlayerGainedLifeWatcher());

        // Sacrifice two Blood tokens: Transform Restless Bloodseeker. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                new TransformSourceEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(2, filter))
        ));
    }

    private RestlessBloodseeker(final RestlessBloodseeker card) {
        super(card);
    }

    @Override
    public RestlessBloodseeker copy() {
        return new RestlessBloodseeker(this);
    }
}
