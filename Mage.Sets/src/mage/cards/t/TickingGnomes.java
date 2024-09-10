
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class TickingGnomes extends CardImpl {

    public TickingGnomes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.GNOME);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Echo {3}
        this.addAbility(new EchoAbility("{3}"));
        // Sacrifice Ticking Gnomes: Ticking Gnomes deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1, "it"), new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private TickingGnomes(final TickingGnomes card) {
        super(card);
    }

    @Override
    public TickingGnomes copy() {
        return new TickingGnomes(this);
    }
}
