
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ChromaOutrageShamanCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth

 */
public final class OutrageShaman extends CardImpl {

    public OutrageShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Chroma - When Outrage Shaman enters the battlefield, it deals damage to target creature equal to the number of red mana symbols in the mana costs of permanents you control.
        Effect effect = new DamageTargetEffect(new ChromaOutrageShamanCount());
        effect.setText("<i>Chroma</i> &mdash; When Outrage Shaman enters the battlefield, it deals damage to target creature equal to the number of red mana symbols in the mana costs of permanents you control.");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
    }

    public OutrageShaman(final OutrageShaman card) {
        super(card);
    }

    @Override
    public OutrageShaman copy() {
        return new OutrageShaman(this);
    }
}

