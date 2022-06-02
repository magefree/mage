package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KenkuArtificer extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("noncreature artifact");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public KenkuArtificer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Homunculus Servant — When Kenku Artificer enters the battlefield, put three +1/+1 counters on up to one target noncreature artifact. That artifact becomes a 0/0 Homunculus artifact creature with flying.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(3))
        );
        ability.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(0, 0, "", SubType.HOMUNCULUS)
                        .withType(CardType.ARTIFACT)
                        .withAbility(FlyingAbility.getInstance()),
                false, false, Duration.EndOfTurn
        ).setText("That artifact becomes a 0/0 Homunculus artifact creature with flying"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ARTIFACTS_NON_CREATURE));
        this.addAbility(ability.withFlavorWord("Homunculus Servant"));
    }

    private KenkuArtificer(final KenkuArtificer card) {
        super(card);
    }

    @Override
    public KenkuArtificer copy() {
        return new KenkuArtificer(this);
    }
}
