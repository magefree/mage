package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author awjackson
 */
public final class BargainingTable extends CardImpl {

    public BargainingTable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {X}, {T}: Draw a card. X is the number of cards in an opponent's hand.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("X is the number of cards in an opponent's hand"));
        // You choose an opponent on announcement. This is not targeted, but a choice is still made.
        // This choice is made before determining the value for X that is used in the cost. (2004-10-04)
        ability.addTarget(new TargetOpponent(true));
        ability.setCostAdjuster(BargainingTableAdjuster.instance);
        this.addAbility(ability);
    }

    private BargainingTable(final BargainingTable card) {
        super(card);
    }

    @Override
    public BargainingTable copy() {
        return new BargainingTable(this);
    }
}

enum BargainingTableAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int handSize = Integer.MAX_VALUE;
        if (game.inCheckPlayableState()) {
            for (UUID playerId : CardUtil.getAllPossibleTargets(ability, game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    handSize = Math.min(handSize, player.getHand().size());
                }
            }
        } else {
            Player player = game.getPlayer(ability.getFirstTarget());
            if (player != null) {
                handSize = player.getHand().size();
            }
        }
        ability.getManaCostsToPay().clear();
        ability.getManaCostsToPay().add(new GenericManaCost(handSize));
    }
}
