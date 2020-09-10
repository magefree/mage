package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoreseekersStone extends CardImpl {

    public LoreseekersStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // {3}, {T}: Draw three cards. This ability costs {1} more to activate for each card in your hand.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(3), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {1} more to activate for each card in your hand."));
        ability.setCostAdjuster(LoreseekersStoneAdjuster.instance);
        this.addAbility(ability);
    }

    private LoreseekersStone(final LoreseekersStone card) {
        super(card);
    }

    @Override
    public LoreseekersStone copy() {
        return new LoreseekersStone(this);
    }
}

enum LoreseekersStoneAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player != null) {
            CardUtil.increaseCost(ability, player.getHand().size());
        }
    }
}
