
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MeditateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class JediBattleHealer extends CardImpl {

    public JediBattleHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.MIRIALAN);
        this.subtype.add(SubType.JEDI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        
        // When Jedi Battle Healer enters the battlefield, you gain 3 life.
         this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));      
        
        // Meditate {1}{W}
        this.addAbility(new MeditateAbility(new ManaCostsImpl<>("{1}{W}")));
    }

    private JediBattleHealer(final JediBattleHealer card) {
        super(card);
    }

    @Override
    public JediBattleHealer copy() {
        return new JediBattleHealer(this);
    }
}
