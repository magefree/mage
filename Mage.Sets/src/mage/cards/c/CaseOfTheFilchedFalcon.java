package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

/**
 *
 * @author DominionSpy
 */
public final class CaseOfTheFilchedFalcon extends CardImpl {

    private static final FilterPermanent filter =
            new FilterArtifactPermanent("noncreature artifact");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public CaseOfTheFilchedFalcon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.CASE);

        // When this Case enters the battlefield, investigate.
        Ability initialAbility = new EntersBattlefieldTriggeredAbility(new InvestigateEffect());
        // To solve -- You control three or more artifacts.
        Condition toSolveCondition = new PermanentsOnTheBattlefieldCondition(new FilterArtifactPermanent(), ComparisonType.OR_GREATER, 3, true);
        // Solved -- {2}{U}, Sacrifice this Case: Put four +1/+1 counters on target noncreature artifact. It becomes a 0/0 Bird creature with flying in addition to its other types.
        Ability solvedAbility = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(4)),
                new ManaCostsImpl<>("{2}{U}"));
        solvedAbility.addEffect(new BecomesCreatureTargetEffect(new CaseOfTheFilchedFalconToken(),
                false, false, Duration.WhileOnBattlefield));
        solvedAbility.addCost(new SacrificeSourceCost());
        solvedAbility.addTarget(new TargetPermanent(filter));

        this.addAbility(new CaseAbility(initialAbility, toSolveCondition, solvedAbility));
    }

    private CaseOfTheFilchedFalcon(final CaseOfTheFilchedFalcon card) {
        super(card);
    }

    @Override
    public CaseOfTheFilchedFalcon copy() {
        return new CaseOfTheFilchedFalcon(this);
    }
}

class CaseOfTheFilchedFalconToken extends TokenImpl {

    public CaseOfTheFilchedFalconToken() {
        super("", "0/0 Bird creature with flying");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.addAbility(FlyingAbility.getInstance());
    }

    private CaseOfTheFilchedFalconToken(final CaseOfTheFilchedFalconToken token) {
        super(token);
    }

    @Override
    public CaseOfTheFilchedFalconToken copy() {
        return new CaseOfTheFilchedFalconToken(this);
    }
}
