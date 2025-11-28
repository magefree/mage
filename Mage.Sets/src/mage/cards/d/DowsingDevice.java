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
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DowsingDevice extends TransformingDoubleFacedCard {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, ComparisonType.MORE_THAN, 3
    );

    public DowsingDevice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{R}",
                "Geode Grotto",
                new CardType[]{CardType.LAND}, new SubType[]{SubType.CAVE}, "");

        // Dowsing Device
        // Whenever Dowsing Device or another artifact you control enters, up to one target creature you control gets +1/+0 and gains haste until end of turn. Then transform Dowsing Device if you control four or more artifacts.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new BoostTargetEffect(1, 0)
                        .setText("up to one target creature you control gets +1/+0"),
                StaticFilters.FILTER_PERMANENT_ARTIFACT, false, true
        );
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText("and gains haste until end of turn"));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition, "Then transform {this} if you control four or more artifacts"
        ));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.getLeftHalfCard().addAbility(ability.addHint(ArtifactYouControlHint.instance));

        // Geode Grotto
        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new mage.abilities.mana.RedManaAbility());

        // {2}{R}, {T}: Until end of turn, target creature gains haste and gets +X/+0, where X is the number of artifacts you control. Activate only as a sorcery.
        Ability ability2 = new mage.abilities.common.ActivateAsSorceryActivatedAbility(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText("Until end of turn, target creature gains haste"), new mage.abilities.costs.mana.ManaCostsImpl<>("{2}{R}"));
        ability2.addCost(new mage.abilities.costs.common.TapSourceCost());
        ability2.addEffect(new mage.abilities.effects.common.continuous.BoostTargetEffect(
                mage.abilities.dynamicvalue.common.ArtifactYouControlCount.instance, mage.abilities.dynamicvalue.common.StaticValue.get(0)
        ).setText("and gets +X/+0, where X is the number of artifacts you control"));
        ability2.addTarget(new mage.target.common.TargetCreaturePermanent());
        this.getRightHalfCard().addAbility(ability2.addHint(ArtifactYouControlHint.instance));
    }

    private DowsingDevice(final DowsingDevice card) {
        super(card);
    }

    @Override
    public DowsingDevice copy() {
        return new DowsingDevice(this);
    }
}
