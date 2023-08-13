
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Valleymaker extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Mountain");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("a Forest");
    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
        filter2.add(SubType.FOREST.getPredicate());
    }

    public Valleymaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R/G}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {tap}, Sacrifice a Mountain: Valleymaker deals 3 damage to target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // {tap}, Sacrifice a Forest: Choose a player. That player adds {G}{G}{G}.
        Ability ability2 = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaToManaPoolTargetControllerEffect(Mana.GreenMana(3), "chosen player")
                .setText("choose a player. That player adds {G}{G}{G}"), new TapSourceCost());
        ability2.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        ability2.addTarget(new TargetPlayer(1, 1, true));
        this.addAbility(ability2);
    }

    private Valleymaker(final Valleymaker card) {
        super(card);
    }

    @Override
    public Valleymaker copy() {
        return new Valleymaker(this);
    }
}
