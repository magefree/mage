
package mage.cards.h;

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
public final class HissingIguanar extends CardImpl {

    public HissingIguanar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.LIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever another creature dies, you may have Hissing Iguanar deal 1 damage to target player.
        DiesCreatureTriggeredAbility ability = new DiesCreatureTriggeredAbility(new DamageTargetEffect(1).setText("you may have {this} deal 1 damage to target player or planeswalker"), true, true);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private HissingIguanar(final HissingIguanar card) {
        super(card);
    }

    @Override
    public HissingIguanar copy() {
        return new HissingIguanar(this);
    }
}
