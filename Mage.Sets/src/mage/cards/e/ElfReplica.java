
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author Loki
 */
public final class ElfReplica extends CardImpl {

    public ElfReplica(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
    }

    private ElfReplica(final ElfReplica card) {
        super(card);
    }

    @Override
    public ElfReplica copy() {
        return new ElfReplica(this);
    }
}
