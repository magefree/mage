package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;

/**
 * @author jeffwadsworth
 */
public final class FieryBombardment extends CardImpl {

    public FieryBombardment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Chroma - {2}, Sacrifice a creature: Fiery Bombardment deals damage to any target equal to the number of red mana symbols in the sacrificed creature's mana cost.
        Effect effect = new FieryBombardmentEffect();
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        ability.addTarget(new TargetAnyTarget());
        ability.setAbilityWord(AbilityWord.CHROMA);
        this.addAbility(ability);

    }

    private FieryBombardment(final FieryBombardment card) {
        super(card);
    }

    @Override
    public FieryBombardment copy() {
        return new FieryBombardment(this);
    }
}

class FieryBombardmentEffect extends OneShotEffect {

    public FieryBombardmentEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage to any target equal to the number of red mana symbols in the sacrificed creature's mana cost.";
    }

    public FieryBombardmentEffect(final FieryBombardmentEffect effect) {
        super(effect);
    }

    @Override
    public FieryBombardmentEffect copy() {
        return new FieryBombardmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                for (Permanent permanent : ((SacrificeTargetCost) cost).getPermanents()) {
                    if (permanent != null) {
                        damage = permanent.getManaCost().getMana().getRed();
                    }
                }
            }
        }
        if (damage > 0) {
            Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (player != null) {
                player.damage(damage, source.getSourceId(), source, game);
            } else {
                Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                if (creature != null) {
                    creature.damage(damage, source.getSourceId(), source, game, false, true);
                }
            }
        }
        return true;
    }
}
