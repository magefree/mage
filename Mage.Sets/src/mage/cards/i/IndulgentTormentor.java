
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public final class IndulgentTormentor extends CardImpl {

    public IndulgentTormentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, draw a card unless target opponent sacrifices a creature or pays 3 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new IndulgentTormentorEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private IndulgentTormentor(final IndulgentTormentor card) {
        super(card);
    }

    @Override
    public IndulgentTormentor copy() {
        return new IndulgentTormentor(this);
    }
}

class IndulgentTormentorEffect extends OneShotEffect {

    IndulgentTormentorEffect() {
        super(Outcome.DrawCard);
        this.staticText = "draw a card unless target opponent sacrifices a creature or pays 3 life";
    }

    IndulgentTormentorEffect(final IndulgentTormentorEffect effect) {
        super(effect);
    }

    @Override
    public IndulgentTormentorEffect copy() {
        return new IndulgentTormentorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent != null) {
            Cost cost = new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT);
            if (cost.canPay(source, source, opponent.getId(), game)
                    && opponent.chooseUse(outcome, "Sacrifice a creature to prevent the card draw?", source, game)) {
                if (cost.pay(source, game, source, opponent.getId(), false, null)) {
                    return true;
                }
            }
            cost = new PayLifeCost(3);
            if (cost.canPay(source, source, opponent.getId(), game)
                    && opponent.chooseUse(outcome, "Pay 3 life to prevent the card draw?", source, game)) {
                if (cost.pay(source, game, source, opponent.getId(), false, null)) {
                    return true;
                }
            }
            game.getPlayer(source.getControllerId()).drawCards(1, source, game);
            return true;
        }
        return false;
    }
}
