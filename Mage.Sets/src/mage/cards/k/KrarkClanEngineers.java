
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class KrarkClanEngineers extends CardImpl {

    public KrarkClanEngineers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}, Sacrifice two artifacts: Destroy target artifact.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledArtifactPermanent("two artifacts"), true)));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
        
    }

    private KrarkClanEngineers(final KrarkClanEngineers card) {
        super(card);
    }

    @Override
    public KrarkClanEngineers copy() {
        return new KrarkClanEngineers(this);
    }
}
