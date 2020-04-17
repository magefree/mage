package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SouvenirSnatcher extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("noncreature artifact");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public SouvenirSnatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Mutate {5}{U}
        this.addAbility(new MutateAbility(this, "{5}{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature mutates, gain control of target noncreature artifact.
        Ability ability = new MutatesSourceTriggeredAbility(new GainControlTargetEffect(Duration.Custom));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SouvenirSnatcher(final SouvenirSnatcher card) {
        super(card);
    }

    @Override
    public SouvenirSnatcher copy() {
        return new SouvenirSnatcher(this);
    }
}
