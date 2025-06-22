package mage.cards.b;

import java.util.UUID;

import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author oscscull
 */
public final class BottomlessPoolLockerRoom extends RoomCard {

    public BottomlessPoolLockerRoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[] { CardType.ENCHANTMENT }, new CardType[] { CardType.ENCHANTMENT },
                "{U}", "{4}{U}", SpellAbilityType.SPLIT);
        this.subtype.add(SubType.ROOM);

        // Left half ability - "When you unlock this door" trigger (delayed triggered
        // ability)
        UnlockThisDoorTriggeredAbility left = new UnlockThisDoorTriggeredAbility(
                new ReturnToHandTargetEffect(), false, true);
        left.addTarget(new TargetCreaturePermanent(0, 1));

        // Right half ability - "Whenever creatures you control deal combat damage"
        DealsDamageToAPlayerAllTriggeredAbility right = new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.PLAYER, true, true, TargetController.OPPONENT);

        this.AddRoomAbilities(left, right);
    }

    private BottomlessPoolLockerRoom(final BottomlessPoolLockerRoom card) {
        super(card);
    }

    @Override
    public BottomlessPoolLockerRoom copy() {
        return new BottomlessPoolLockerRoom(this);
    }
}