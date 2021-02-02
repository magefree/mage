
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ShardOfBrokenGlass extends CardImpl {

    public ShardOfBrokenGlass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));

        // Whenever equipped creature attacks, you may put the top two cards of your library into your graveyard.
        this.addAbility(new AttacksAttachedTriggeredAbility(new MillCardsControllerEffect(2), true));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    private ShardOfBrokenGlass(final ShardOfBrokenGlass card) {
        super(card);
    }

    @Override
    public ShardOfBrokenGlass copy() {
        return new ShardOfBrokenGlass(this);
    }
}
