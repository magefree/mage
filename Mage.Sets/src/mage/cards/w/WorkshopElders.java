package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WorkshopElders extends CardImpl {

    private static final FilterPermanent filter2
            = new FilterControlledArtifactPermanent("noncreature artifact you control");

    static {
        filter2.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public WorkshopElders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Artifact creatures you control have flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));

        // At the beginning of combat on your turn, you may have target noncreature artifact you control become a 0/0 artifact creature. If you do, put four +1/+1 counters on it.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCardTypeTargetEffect(
                Duration.EndOfGame, CardType.ARTIFACT, CardType.CREATURE
        ).setText("target noncreature artifact you control become a 0/0 artifact creature"), TargetController.YOU, true);
        ability.addEffect(new SetBasePowerToughnessTargetEffect(
                0, 0, Duration.EndOfGame
        ).setText("If you do"));
        ability.addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(4)
        ).setText(", put four +1/+1 counters on it"));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private WorkshopElders(final WorkshopElders card) {
        super(card);
    }

    @Override
    public WorkshopElders copy() {
        return new WorkshopElders(this);
    }
}
