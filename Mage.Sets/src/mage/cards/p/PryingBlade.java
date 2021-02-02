
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author TheElk801
 */
public final class PryingBlade extends CardImpl {

    public PryingBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));

        // Whenever equipped creature deals combat damage to a player, create a colorless Treasure artifact token with "{t}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(new CreateTokenEffect(new TreasureToken()), "equipped creature", false));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private PryingBlade(final PryingBlade card) {
        super(card);
    }

    @Override
    public PryingBlade copy() {
        return new PryingBlade(this);
    }
}
