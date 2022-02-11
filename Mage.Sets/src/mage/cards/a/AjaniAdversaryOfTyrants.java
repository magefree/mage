package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.command.emblems.AjaniAdversaryOfTyrantsEmblem;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class AjaniAdversaryOfTyrants extends CardImpl {

    private static final FilterCreatureCard filter
            = new FilterCreatureCard("creature card with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public AjaniAdversaryOfTyrants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);
        this.setStartingLoyalty(4);

        // +1: Put a +1/+1 counter on each of up to two target creatures.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), 1);
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);

        // −2: Return target creature card with converted mana cost 2 or less from your graveyard to the battlefield.
        ability = new LoyaltyAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), -2);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // −7: You get an emblem with "At the beginning of your end step, create three 1/1 white Cat creature tokens with lifelink."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new AjaniAdversaryOfTyrantsEmblem()), -7));
    }

    private AjaniAdversaryOfTyrants(final AjaniAdversaryOfTyrants card) {
        super(card);
    }

    @Override
    public AjaniAdversaryOfTyrants copy() {
        return new AjaniAdversaryOfTyrants(this);
    }
}
