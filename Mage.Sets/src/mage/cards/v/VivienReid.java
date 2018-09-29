package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.command.emblems.VivienReidEmblem;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class VivienReid extends CardImpl {

    private static final FilterCard filter = new FilterCard("a creature or land card");
    private static final FilterPermanent filter2 = new FilterPermanent("artifact, enchantment, or creature with flying");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)
        ));
        filter2.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.ENCHANTMENT),
                Predicates.and(
                        new CardTypePredicate(CardType.CREATURE),
                        new AbilityPredicate(FlyingAbility.class)
                )
        ));
    }

    public VivienReid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VIVIEN);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

        // +1: Look at the top four cards of your library. You may reveal a creature or land card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(
                new LookLibraryAndPickControllerEffect(
                        new StaticValue(4), false, new StaticValue(1), filter,
                        Zone.LIBRARY, false, true, false, Zone.HAND, true, false, false)
                        .setBackInRandomOrder(true)
                        .setText("Look at the top four cards of your library. You may reveal a creature or land card from among them"
                                + " and put it into your hand. Put the rest on the bottom of your library in a random order."), 1
        ));

        // -3: Destroy target artifact, enchantment, or creature with flying.
        Ability ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);

        // -8: You get an emblem with "Creatures you control get +2/+2 and have vigilance, trample, and indestructible.
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new VivienReidEmblem()), -8));
    }

    public VivienReid(final VivienReid card) {
        super(card);
    }

    @Override
    public VivienReid copy() {
        return new VivienReid(this);
    }
}
