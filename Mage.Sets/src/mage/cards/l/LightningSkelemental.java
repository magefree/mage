package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightningSkelemental extends CardImpl {

    public LightningSkelemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Lightning Skelemental deals combat damage to a player, that player discards two cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DiscardTargetEffect(2), false, true
        ));

        // At the beginning of the end step, sacrifice Lightning Skelemental.
        this.addAbility(new OnEventTriggeredAbility(
                GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step",
                true, new SacrificeSourceEffect()
        ));
    }

    private LightningSkelemental(final LightningSkelemental card) {
        super(card);
    }

    @Override
    public LightningSkelemental copy() {
        return new LightningSkelemental(this);
    }
}
