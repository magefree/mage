package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class NissaGenesisMage extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature and/or land cards");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public NissaGenesisMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{5}{G}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NISSA);

        this.setStartingLoyalty(5);

        //+2: Untap up to two target creatures and up to two target lands.
        Effect effect = new UntapTargetEffect("untap up to two target creatures and up to two target lands");
        effect.setTargetPointer(new EachTargetPointer());
        Ability ability = new LoyaltyAbility(effect, +2);
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        ability.addTarget(new TargetLandPermanent(0, 2));
        this.addAbility(ability);

        //-3: Target creature gets +5/+5 until end of turn.
        ability = new LoyaltyAbility(new BoostTargetEffect(5, 5, Duration.EndOfTurn), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        //-10: Look at the top ten cards of your library.
        //You may put any number of creature and/or land cards from among them onto the battlefield.
        //Put the rest on the bottom of your library in a random order.);
        this.addAbility(new LoyaltyAbility(
                new LookLibraryAndPickControllerEffect(10, Integer.MAX_VALUE, filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM),
                -10));
    }

    private NissaGenesisMage(final NissaGenesisMage card) {
        super(card);
    }

    @Override
    public NissaGenesisMage copy() {
        return new NissaGenesisMage(this);
    }
}
