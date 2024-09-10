package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetSacrifice;

/**
 *
 * @author LevelX2
 */
public final class DevouringGreed extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SPIRIT, "any number of Spirits");

    public DevouringGreed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");
        this.subtype.add(SubType.ARCANE);

        // As an additional cost to cast Devouring Greed, you may sacrifice any number of Spirits.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetSacrifice(0, Integer.MAX_VALUE, filter)));

        // Target player loses 2 life plus 2 life for each Spirit sacrificed this way. You gain that much life.
        this.getSpellAbility().addEffect(new DevouringGreedEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private DevouringGreed(final DevouringGreed card) {
        super(card);
    }

    @Override
    public DevouringGreed copy() {
        return new DevouringGreed(this);
    }
}

class DevouringGreedEffect extends OneShotEffect {

    DevouringGreedEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Target player loses 2 life plus 2 life for each Spirit sacrificed this way. You gain that much life";
    }

    private DevouringGreedEffect(final DevouringGreedEffect effect) {
        super(effect);
    }

    @Override
    public DevouringGreedEffect copy() {
        return new DevouringGreedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numberSpirits = 0;
        for (Cost cost :source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                numberSpirits += ((SacrificeTargetCost) cost).getPermanents().size();
            }
        }
        int amount = 2 + (numberSpirits * 2);
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && sourcePlayer != null) {
            targetPlayer.loseLife(amount, game, source, false);
            sourcePlayer.gainLife(amount, game, source);
            return true;
        }
        return false;
    }
}
