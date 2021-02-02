
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author North
 */
public final class RageThrower extends CardImpl {

    public RageThrower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever another creature dies, Rage Thrower deals 2 damage to target player.
        DiesCreatureTriggeredAbility ability = new DiesCreatureTriggeredAbility(new DamageTargetEffect(2), false, true);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private RageThrower(final RageThrower card) {
        super(card);
    }

    @Override
    public RageThrower copy() {
        return new RageThrower(this);
    }
}
