
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GoblinChirurgeon extends CardImpl {

    public GoblinChirurgeon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Sacrifice a Goblin: Regenerate target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new RegenerateTargetEffect(),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(new FilterControlledCreaturePermanent(SubType.GOBLIN,"a Goblin"))));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GoblinChirurgeon(final GoblinChirurgeon card) {
        super(card);
    }

    @Override
    public GoblinChirurgeon copy() {
        return new GoblinChirurgeon(this);
    }
}
