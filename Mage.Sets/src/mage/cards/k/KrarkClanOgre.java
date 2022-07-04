
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class KrarkClanOgre extends CardImpl {

    public KrarkClanOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.OGRE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {R}, Sacrifice an artifact: Target creature can't block this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBlockTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, new FilterControlledArtifactPermanent("an artifact"), true)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KrarkClanOgre(final KrarkClanOgre card) {
        super(card);
    }

    @Override
    public KrarkClanOgre copy() {
        return new KrarkClanOgre(this);
    }
}
