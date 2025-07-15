package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SynthesizerLabship extends CardImpl {

    public SynthesizerLabship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");

        this.subtype.add(SubType.SPACECRAFT);

        // Station
        this.addAbility(new StationAbility());

        // STATION 2+
        // At the beginning of combat on your turn, up to one other target artifact you control becomes an artifact creature with base power and toughness 2/2 and gains flying until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCardTypeTargetEffect(
                Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
        ).setText("up to one other target artifact you control becomes an artifact creature"));
        ability.addEffect(new SetBasePowerToughnessTargetEffect(2, 2, Duration.EndOfTurn)
                .setText("with base power and toughness 2/2"));
        ability.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText("and gains flying until end of turn"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT));
        this.addAbility(new StationLevelAbility(2).withLevelAbility(ability));

        // STATION 9+
        // Flying
        // Vigilance
        // 4/4
        this.addAbility(new StationLevelAbility(9)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(VigilanceAbility.getInstance())
                .withPT(4, 4));
    }

    private SynthesizerLabship(final SynthesizerLabship card) {
        super(card);
    }

    @Override
    public SynthesizerLabship copy() {
        return new SynthesizerLabship(this);
    }
}
