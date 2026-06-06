package mage.cards.r;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.OmenCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class RunescaleStormbrood extends OmenCard {

    private static final FilterSpell filter = new FilterSpell("spell with mana value 2 or less");
    private static final FilterSpell castFilter = new FilterSpell("a noncreature spell or a Dragon spell");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 2));
        castFilter.add(Predicates.or(
                Predicates.not(CardType.CREATURE.getPredicate()),
                SubType.DRAGON.getPredicate()
        ));
    }

    public RunescaleStormbrood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{3}{R}",
                "Chilling Screech",
                new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Runescale Stormbrood
        this.getLeftHalfCard().setPT(2, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell or a Dragon spell, this creature gets +2/+0 until end of turn.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), castFilter, false));

        // Chilling Screech
        // Counter target spell with mana value 2 or less.
        this.getRightHalfCard().getSpellAbility().addEffect(new CounterTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetSpell(filter));

        finalizeCard();
    }

    private RunescaleStormbrood(final RunescaleStormbrood card) {
        super(card);
    }

    @Override
    public RunescaleStormbrood copy() {
        return new RunescaleStormbrood(this);
    }
}
