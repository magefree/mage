package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeAllCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Soulblast extends CardImpl {

    public Soulblast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{R}{R}");

        // As an additional cost to cast Soulblast, sacrifice all creatures you control.
        this.getSpellAbility().addCost(new SacrificeAllCost(StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED));

        // Soulblast deals damage to any target equal to the total power of the sacrificed creatures.
        this.getSpellAbility().addEffect(new SoulblastEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private Soulblast(final Soulblast card) {
        super(card);
    }

    @Override
    public Soulblast copy() {
        return new Soulblast(this);
    }
}

class SoulblastEffect extends OneShotEffect {

    public SoulblastEffect() {
        super(Outcome.Benefit);
        this.staticText = "Soulblast deals damage to any target equal to the total power of the sacrificed creatures";
    }

    private SoulblastEffect(final SoulblastEffect effect) {
        super(effect);
    }

    @Override
    public SoulblastEffect copy() {
        return new SoulblastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int power = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeAllCost) {
                for (Permanent permanent : ((SacrificeAllCost) cost).getPermanents()) {
                    power += permanent.getPower().getValue();
                }
            }
        }
        if (power > 0) {
            Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (player != null) {
                player.damage(power, source.getSourceId(), source, game);
            } else {
                Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                if (creature != null) {
                    creature.damage(power, source.getSourceId(), source, game, false, true);
                }
            }
        }
        return true;
    }
}
