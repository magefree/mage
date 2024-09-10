
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author fireshoes
 */
public final class RadhaHeirToKeld extends CardImpl {

    public RadhaHeirToKeld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Radha, Heir to Keld attacks, you may add {R}{R}.
        Ability ability = new AttacksTriggeredAbility(new BasicManaEffect(Mana.RedMana(2)), true);
        this.addAbility(ability);
        
        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private RadhaHeirToKeld(final RadhaHeirToKeld card) {
        super(card);
    }

    @Override
    public RadhaHeirToKeld copy() {
        return new RadhaHeirToKeld(this);
    }
}
