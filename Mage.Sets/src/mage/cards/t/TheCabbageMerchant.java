package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DealsDamageToYouAllTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheCabbageMerchant extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.FOOD, "untapped Foods you control");
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.FOOD, "Food token");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter2.add(TokenPredicate.TRUE);
    }

    public TheCabbageMerchant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an opponent casts a noncreature spell, create a Food token.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new CreateTokenEffect(new FoodToken()), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // Whenever a creature deals combat damage to you, sacrifice a Food token.
        this.addAbility(new DealsDamageToYouAllTriggeredAbility(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                new SacrificeControllerEffect(filter2, 1, ""), true
        ));

        // Tap two untapped Foods you control: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new TapTargetCost(new TargetControlledPermanent(2, filter))));
    }

    private TheCabbageMerchant(final TheCabbageMerchant card) {
        super(card);
    }

    @Override
    public TheCabbageMerchant copy() {
        return new TheCabbageMerchant(this);
    }
}
