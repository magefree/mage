
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class JundSojourners extends CardImpl {

    public JundSojourners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{R}{G}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);



        
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When you cycle Jund Sojourners or it dies, you may have it deal 1 damage to any target.
        Ability ability1 = new CycleTriggeredAbility(new DamageTargetEffect(1));
        Ability ability2 = new DiesSourceTriggeredAbility(new DamageTargetEffect(1));
        ability1.addTarget(new TargetAnyTarget());
        ability2.addTarget(new TargetAnyTarget());
        this.addAbility(ability1);
        this.addAbility(ability2);
        
        // Cycling {2}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}{R}")));
    }

    private JundSojourners(final JundSojourners card) {
        super(card);
    }

    @Override
    public JundSojourners copy() {
        return new JundSojourners(this);
    }
}
