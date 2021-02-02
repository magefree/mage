
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LoneFox
 */
public final class WuScout extends CardImpl {

    public WuScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
        // When Wu Scout enters the battlefield, look at target opponent's hand.
        Effect effect = new LookAtTargetPlayerHandEffect();
        effect.setText("look at target opponent's hand");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private WuScout(final WuScout card) {
        super(card);
    }

    @Override
    public WuScout copy() {
        return new WuScout(this);
    }
}
