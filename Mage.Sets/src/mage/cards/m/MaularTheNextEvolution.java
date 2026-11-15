package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.RulebreakerAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class MaularTheNextEvolution extends CardImpl {

    private static final FilterCard cardFilter = new FilterCard("can have creature cards with mana value 7 or greater of any color identity and any basic land cards");
    private static final FilterControlledCreaturePermanent creatureFilter = new FilterControlledCreaturePermanent("a creature you control with mana value 7 or greater");

    static {
        cardFilter.add(Predicates.or(
            Predicates.and(
                CardType.CREATURE.getPredicate(),
                new ManaValuePredicate(ComparisonType.OR_GREATER, 7)
            ),
            SuperType.BASIC.getPredicate()
        ));
        creatureFilter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 7));
    }

    public MaularTheNextEvolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Rulebreaker -- A deck with this commander can have creature cards with mana value 7 or greater of any color identity and any basic land cards.
        this.addAbility(new RulebreakerAbility(cardFilter));

        // Whenever a creature you control with mana value 7 or greater attacks, double its power and toughness until end of turn.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
            new MaularTheNextEvolutionEffect(),
            false, creatureFilter, true
        ));
    }

    private MaularTheNextEvolution(final MaularTheNextEvolution card) {
        super(card);
    }

    @Override
    public MaularTheNextEvolution copy() {
        return new MaularTheNextEvolution(this);
    }
}

class MaularTheNextEvolutionEffect extends OneShotEffect {

    MaularTheNextEvolutionEffect() {
        super(Outcome.Benefit);
        staticText = "double its power and toughness until end of turn";
    }

    private MaularTheNextEvolutionEffect(final MaularTheNextEvolutionEffect effect) {
        super(effect);
    }

    @Override
    public MaularTheNextEvolutionEffect copy() {
        return new MaularTheNextEvolutionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        ContinuousEffect boost = new BoostTargetEffect(
            permanent.getPower().getValue(),
            permanent.getToughness().getValue()
        ).setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(boost, source);
        return true;
    }
}
