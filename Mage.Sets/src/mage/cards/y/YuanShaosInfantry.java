
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAloneSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public final class YuanShaosInfantry extends CardImpl {

    public YuanShaosInfantry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Yuan Shao's Infantry attacks alone, Yuan Shao's Infantry can't be blocked this combat.
        Effect effect = new CantBeBlockedSourceEffect(Duration.EndOfCombat);
        effect.setText("{this} can't be blocked this combat");
        this.addAbility(new AttacksAloneSourceTriggeredAbility(effect).setReplaceRuleText(false));
    }

    private YuanShaosInfantry(final YuanShaosInfantry card) {
        super(card);
    }

    @Override
    public YuanShaosInfantry copy() {
        return new YuanShaosInfantry(this);
    }
}
