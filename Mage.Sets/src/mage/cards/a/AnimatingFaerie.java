package mage.cards.a;

import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnimatingFaerie extends AdventureCard {

    private static final FilterPermanent filter
            = new FilterControlledArtifactPermanent("noncreature artifact you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public AnimatingFaerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FAERIE}, "{2}{U}",
                "Bring to Life",
                new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Animating Faerie
        this.getLeftHalfCard().setPT(2, 2);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Bring to Life
        // Target noncreature artifact you control becomes a 0/0 artifact creature. Put four +1/+1 counters on it.
        this.getRightHalfCard().getSpellAbility().addEffect(new AddCardTypeTargetEffect(
                Duration.EndOfGame, CardType.ARTIFACT, CardType.CREATURE
        ).setText("Target noncreature artifact you control becomes a 0/0 artifact creature"));
        this.getRightHalfCard().getSpellAbility().addEffect(new SetBasePowerToughnessTargetEffect(
                0, 0, Duration.EndOfGame
        ).setText("Put four +1/+1 counters on it."));
        this.getRightHalfCard().getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(4)
        ).setText(" "));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter));

        finalizeCard();
    }

    private AnimatingFaerie(final AnimatingFaerie card) {
        super(card);
    }

    @Override
    public AnimatingFaerie copy() {
        return new AnimatingFaerie(this);
    }
}
