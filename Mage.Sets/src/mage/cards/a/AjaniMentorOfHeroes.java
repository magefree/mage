
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author LevelX2
 */
public final class AjaniMentorOfHeroes extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");
    private static final FilterCard filterCard = new FilterCard("an Aura, creature, or planeswalker card");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filterCard.add(Predicates.or(
                SubType.AURA.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()));
    }

    public AjaniMentorOfHeroes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{3}{G}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);

        this.setStartingLoyalty(4);

        // +1: Distribute three +1/+1 counters among one, two, or three target creatures you control
        Ability ability = new LoyaltyAbility(new DistributeCountersEffect(CounterType.P1P1, 3, false, "one, two, or three target creatures you control"), 1);
        ability.addTarget(new TargetCreaturePermanentAmount(3, filter));
        this.addAbility(ability);

        // +1: Look at the top four cards of your library. You may reveal an Aura, creature, or planeswalker card
        // from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(4, 1, filterCard, PutCards.HAND, PutCards.BOTTOM_ANY), 1));

        // -8: You gain 100 life.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(100), -8));
    }

    private AjaniMentorOfHeroes(final AjaniMentorOfHeroes card) {
        super(card);
    }

    @Override
    public AjaniMentorOfHeroes copy() {
        return new AjaniMentorOfHeroes(this);
    }
}
