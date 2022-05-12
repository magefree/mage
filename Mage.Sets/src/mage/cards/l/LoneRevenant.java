package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author awjackson
 */
public final class LoneRevenant extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, ComparisonType.EQUAL_TO, 0);

    public LoneRevenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(HexproofAbility.getInstance());

        // Whenever Lone Revenant deals combat damage to a player,
        // if you control no other creatures, look at the top four cards of your library.
        // Put one of them into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new LookLibraryAndPickControllerEffect(4, 1, PutCards.HAND, PutCards.BOTTOM_ANY),
                        false
                ), condition, "Whenever {this} deals combat damage to a player, " +
                "if you control no other creatures, look at the top four cards of your library. " +
                "Put one of them into your hand and the rest on the bottom of your library in any order."
        ));
    }

    private LoneRevenant(final LoneRevenant card) {
        super(card);
    }

    @Override
    public LoneRevenant copy() {
        return new LoneRevenant(this);
    }
}
