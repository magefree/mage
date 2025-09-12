package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class LoneRevenant extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("you control no other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public LoneRevenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(HexproofAbility.getInstance());

        // Whenever Lone Revenant deals combat damage to a player,
        // if you control no other creatures, look at the top four cards of your library.
        // Put one of them into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, PutCards.HAND, PutCards.BOTTOM_ANY
        )).withInterveningIf(condition));
    }

    private LoneRevenant(final LoneRevenant card) {
        super(card);
    }

    @Override
    public LoneRevenant copy() {
        return new LoneRevenant(this);
    }
}
