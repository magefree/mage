// import java.util.UUID;
// import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
// import mage.abilities.common.UnlockDoorTriggeredAbility;
// import mage.abilities.effects.common.DrawCardSourceControllerEffect;
// import mage.abilities.effects.common.ReturnToHandTargetEffect;
// import mage.cards.CardSetInfo;
// import mage.cards.RoomCard;
// import mage.constants.CardType;
// import mage.constants.SetTargetPointer;
// import mage.constants.SubType;
// import mage.constants.TargetController;
// import mage.filter.StaticFilters;
// import mage.target.common.TargetCreaturePermanent;

// /**
//  * @author oscscull
//  */
// public final class BottomlessPoolLockerRoom extends RoomCard {

//     public BottomlessPoolLockerRoom(UUID ownerId, CardSetInfo setInfo) {
//         super(ownerId, setInfo,
//                 new CardType[] { CardType.ENCHANTMENT },
//                 "{U}", "{4}{U}");

//         this.subtype.add(SubType.ROOM);

//         // Left half - Bottomless Pool
//         // When you unlock this door, return up to one target creature to its owner's
//         // hand.
//         UnlockDoorTriggeredAbility leftAbility = new UnlockDoorTriggeredAbility(
//                 new ReturnToHandTargetEffect(), false);
//         leftAbility.addTarget(new TargetCreaturePermanent(0, 1));
//         getLeftHalfCard().addAbility(leftAbility);

//         // Right half - Locker Room
//         // Whenever one or more creatures you control deal combat damage to a player,
//         // draw a card.
//         getRightHalfCard().addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
//                 new DrawCardSourceControllerEffect(1),
//                 StaticFilters.FILTER_CONTROLLED_A_CREATURE, true, SetTargetPointer.PLAYER,
//                 true, true, TargetController.OPPONENT));
//     }

//     private BottomlessPoolLockerRoom(final BottomlessPoolLockerRoom card) {
//         super(card);
//     }

//     @Override
//     public BottomlessPoolLockerRoom copy() {
//         return new BottomlessPoolLockerRoom(this);
//     }
// }

package mage.cards.b;

import java.util.UUID;

import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;

/**
 * @author oscscull
 */
public final class BottomlessPoolLockerRoom extends RoomCard {

    public BottomlessPoolLockerRoom(UUID ownerId, CardSetInfo setInfo) {
         super(
                ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new CardType[]{CardType.ENCHANTMENT},
                "{U}", "{4}{U}", SpellAbilityType.SPLIT
        );
        this.subtype.add(SubType.ROOM);

        // No abilities for now - just get basic functionality working
    }

    private BottomlessPoolLockerRoom(final BottomlessPoolLockerRoom card) {
        super(card);
    }

    @Override
    public BottomlessPoolLockerRoom copy() {
        return new BottomlessPoolLockerRoom(this);
    }
}