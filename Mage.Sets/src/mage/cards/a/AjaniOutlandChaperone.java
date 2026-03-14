package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.KithkinGreenWhiteToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AjaniOutlandChaperone extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");
    private static final FilterCard filter2 = new FilterNonlandCard("nonland permanent cards with mana value 3 or less");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter2.add(PermanentPredicate.instance);
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public AjaniOutlandChaperone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);
        this.setStartingLoyalty(3);

        // +1: Create a 1/1 green and white Kithkin creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new KithkinGreenWhiteToken()), 1));

        // -2: Ajani deals 4 damage to target tapped creature.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(4), -2);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // -8: Look at the top X cards of your library, where X is your life total. You may put any number of nonland permanent cards with mana value 3 or less from among them onto the battlefield. Then shuffle.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(
                ControllerLifeCount.instance, Integer.MAX_VALUE,
                filter2, PutCards.BATTLEFIELD, PutCards.SHUFFLE
        ), -8));
    }

    private AjaniOutlandChaperone(final AjaniOutlandChaperone card) {
        super(card);
    }

    @Override
    public AjaniOutlandChaperone copy() {
        return new AjaniOutlandChaperone(this);
    }
}
