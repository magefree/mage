
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 * @author noxx
 */
public final class NecromancersStockpile extends CardImpl {

    public NecromancersStockpile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");

        // {1}{B}, Discard a creature card: Draw a card.
        // If the discarded card was a Zombie card, create a tapped 2/2 black Zombie creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new NecromancersStockpileDiscardTargetCost(new TargetCardInHand(new FilterCreatureCard())));
        ability.addEffect(new NecromancersStockpilePutTokenEffect());
        this.addAbility(ability);
    }

    public NecromancersStockpile(final NecromancersStockpile card) {
        super(card);
    }

    @Override
    public NecromancersStockpile copy() {
        return new NecromancersStockpile(this);
    }

}

class NecromancersStockpileDiscardTargetCost extends CostImpl {

    protected boolean isZombieCard;

    public NecromancersStockpileDiscardTargetCost(TargetCardInHand target) {
        this.addTarget(target);
        this.text = "Discard " + target.getTargetName();
    }

    public NecromancersStockpileDiscardTargetCost(NecromancersStockpileDiscardTargetCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        if (targets.choose(Outcome.Discard, controllerId, sourceId, game)) {
            Player player = game.getPlayer(controllerId);
            for (UUID targetId : targets.get(0).getTargets()) {
                Card card = player.getHand().get(targetId, game);
                if (card == null) {
                    return false;
                }
                isZombieCard = card.hasSubtype(SubType.ZOMBIE, game);
                paid |= player.discard(card, null, game);

            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public NecromancersStockpileDiscardTargetCost copy() {
        return new NecromancersStockpileDiscardTargetCost(this);
    }

    public boolean isZombieCard() {
        return isZombieCard;
    }

}

class NecromancersStockpilePutTokenEffect extends OneShotEffect {

    NecromancersStockpilePutTokenEffect() {
        super(Outcome.Neutral);
        staticText = "If the discarded card was a Zombie card, create a tapped 2/2 black Zombie creature token";
    }

    NecromancersStockpilePutTokenEffect(final NecromancersStockpilePutTokenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        NecromancersStockpileDiscardTargetCost cost = (NecromancersStockpileDiscardTargetCost) source.getCosts().get(0);
        if (cost != null && cost.isZombieCard()) {
            new CreateTokenEffect(new ZombieToken(), 1, true, false).apply(game, source);
        }
        return true;
    }

    @Override
    public NecromancersStockpilePutTokenEffect copy() {
        return new NecromancersStockpilePutTokenEffect(this);
    }
}
