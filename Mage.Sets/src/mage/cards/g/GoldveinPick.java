package mage.cards.g;

import java.util.UUID;

import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author weirddan455
 */
public final class GoldveinPick extends CardImpl {

    public GoldveinPick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Whenever equipped creature deals combat damage to a player, create a Treasure token.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), "equipped creature", false
        ));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private GoldveinPick(final GoldveinPick card) {
        super(card);
    }

    @Override
    public GoldveinPick copy() {
        return new GoldveinPick(this);
    }
}
