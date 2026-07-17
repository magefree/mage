package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
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
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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
        ability.addEffect(new DepthshakerTitanEffect());
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

class DepthshakerTitanEffect extends OneShotEffect {

    DepthshakerTitanEffect() {
        super(Outcome.Sacrifice);
        staticText = "Sacrifice them at the beginning of the next end step";
    }

    private DepthshakerTitanEffect(final DepthshakerTitanEffect effect) {
        super(effect);
    }

    @Override
    public DepthshakerTitanEffect copy() {
        return new DepthshakerTitanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = source.getTargets().get(0).getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice those artifacts");
        sacrificeEffect.setTargetPointer(new FixedTargets(permanents, game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect), source);
        return true;
    }
}
