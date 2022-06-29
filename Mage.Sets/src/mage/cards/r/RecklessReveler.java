
package mage.cards.r;

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
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public final class RecklessReveler extends CardImpl {

    public RecklessReveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.SATYR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {R}, Sacrifice Reckless Reveler: Destroy target artifact.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        
        this.addAbility(ability);
    }

    private RecklessReveler(final RecklessReveler card) {
        super(card);
    }

    @Override
    public RecklessReveler copy() {
        return new RecklessReveler(this);
    }
}
