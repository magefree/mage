
package mage.cards.v;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

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
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(3), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // {tap}, Sacrifice a Forest: Choose a player. That player adds {G}{G}{G}.
        Ability ability2 = new SimpleManaAbility(Zone.BATTLEFIELD, new ValleymakerManaEffect()
                .setText("That player adds {G}{G}{G}"), new TapSourceCost());
        ability2.addCost(new SacrificeTargetCost(filter2));
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

//Based on Spectral Searchlight
class ValleymakerManaEffect extends ManaEffect {

    ValleymakerManaEffect() {
        super();
        this.staticText = "Choose a player. That player adds {G}{G}{G}.";
    }

    private ValleymakerManaEffect(final ValleymakerManaEffect effect) {
        super(effect);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        if (!game.inCheckPlayableState()) {
            TargetPlayer target = new TargetPlayer(1, 1, true);
            if (target.choose(Outcome.PutManaInPool, source.getControllerId(), source, game)) {
                return game.getPlayer(target.getFirstTarget());
            }
        }
        return game.getPlayer(source.getControllerId()); // Count as controller's potential mana for card playability
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        return Mana.GreenMana(3);
    }

    @Override
    public ValleymakerManaEffect copy() {
        return new ValleymakerManaEffect(this);
    }
}
