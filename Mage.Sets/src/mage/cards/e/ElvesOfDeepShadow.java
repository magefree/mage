
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class ElvesOfDeepShadow extends CardImpl {

    public ElvesOfDeepShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add {B}. Elves of Deep Shadow deals 1 damage to you.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new TapSourceCost());
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
    }

    private ElvesOfDeepShadow(final ElvesOfDeepShadow card) {
        super(card);
    }

    @Override
    public ElvesOfDeepShadow copy() {
        return new ElvesOfDeepShadow(this);
    }
}
