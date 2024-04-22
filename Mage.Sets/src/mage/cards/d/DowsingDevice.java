package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DowsingDevice extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, ComparisonType.MORE_THAN, 3
    );

    public DowsingDevice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");
        this.secondSideCardClazz = mage.cards.g.GeodeGrotto.class;

        // Whenever Dowsing Device or another artifact enters the battlefield under your control, up to one target creature you control gets +1/+0 and gains haste until end of turn. Then transform Dowsing Device if you control four or more artifacts.
        this.addAbility(new TransformAbility());
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new BoostTargetEffect(1, 0)
                        .setText("up to one target creature you control gets +1/+0"),
                StaticFilters.FILTER_PERMANENT_ARTIFACT, false, true
        );
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText("and gains haste until end of turn"));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition, "Then " +
                "transform {this} if you control four or more artifacts"
        ));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability.addHint(ArtifactYouControlHint.instance));
    }

    private DowsingDevice(final DowsingDevice card) {
        super(card);
    }

    @Override
    public DowsingDevice copy() {
        return new DowsingDevice(this);
    }
}
