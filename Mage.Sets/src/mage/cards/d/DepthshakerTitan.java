package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MeleeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DepthshakerTitan extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("noncreature artifacts you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public DepthshakerTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When this creature enters, any number of target noncreature artifacts you control become 3/3 artifact creatures. Sacrifice them at the beginning of the next end step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCardTypeTargetEffect(
                Duration.Custom, CardType.ARTIFACT, CardType.CREATURE
        ).setText("any number of target noncreature artifacts you control"));
        ability.addEffect(new SetBasePowerToughnessTargetEffect(
                3, 3, Duration.Custom
        ).setText(" become 3/3 artifact creatures"));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                        new SacrificeTargetEffect().setText("sacrifice those artifacts")
                ), true
        ).withCopyToPointer(true).setText("Sacrifice them at the beginning of the next end step"));
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter));
        this.addAbility(ability);

        // Each artifact creature you control has melee, trample, and haste.
        ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MeleeAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE
        ).setText("each artifact creature you control has melee"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE
        ).setText(", trample"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE
        ).setText(", and haste"));
        this.addAbility(ability);
    }

    private DepthshakerTitan(final DepthshakerTitan card) {
        super(card);
    }

    @Override
    public DepthshakerTitan copy() {
        return new DepthshakerTitan(this);
    }
}
