
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author anonymous
 */
public final class StarlitSanctum extends CardImpl {

    public StarlitSanctum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.CLERIC, "a Cleric creature");
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {W}, {T}, Sacrifice a Cleric creature: You gain life equal to the sacrificed creature's toughness.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StarlitSanctumWhiteEffect(), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
        this.addAbility(ability);
        // {B}, {T}, Sacrifice a Cleric creature: Target player loses life equal to the sacrificed creature's power.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StarlitSanctumBlackEffect(), new ManaCostsImpl<>("{B}"));
        ability.addTarget(new TargetPlayer());
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
        this.addAbility(ability);
    }

    private StarlitSanctum(final StarlitSanctum card) {
        super(card);
    }

    @Override
    public StarlitSanctum copy() {
        return new StarlitSanctum(this);
    }
}

class StarlitSanctumWhiteEffect extends OneShotEffect {

    public StarlitSanctumWhiteEffect() {
        super(Outcome.GainLife);
        staticText = "You gain life equal to the sacrificed creature's toughness";
    }

    private StarlitSanctumWhiteEffect(final StarlitSanctumWhiteEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                amount = ((SacrificeTargetCost) cost).getPermanents().get(0).getToughness().getValue();
                break;
            }
        }
        if (amount > 0) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(amount, game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public StarlitSanctumWhiteEffect copy() {
        return new StarlitSanctumWhiteEffect(this);
    }
}

class StarlitSanctumBlackEffect extends OneShotEffect {

    public StarlitSanctumBlackEffect() {
        super(Outcome.Damage);
        staticText = "Target player loses life equal to the sacrificed creature's power";
    }

    private StarlitSanctumBlackEffect(final StarlitSanctumBlackEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                amount = ((SacrificeTargetCost) cost).getPermanents().get(0).getPower().getValue();
                break;
            }
        }
        if (amount > 0) {
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) {
                player.loseLife(amount, game, source, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public StarlitSanctumBlackEffect copy() {
        return new StarlitSanctumBlackEffect(this);
    }
}
