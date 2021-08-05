package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreasureChest extends CardImpl {

    public TreasureChest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {4}, Sacrifice Treasure Chest: Roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        Ability ability = new SimpleActivatedAbility(effect, new GenericManaCost(4));
        ability.addCost(new SacrificeSourceCost());

        // 1 | Trapped! — You lose 3 life.
        effect.addTableEntry(1, 1, new LoseLifeSourceControllerEffect(3)
                .setText(CardUtil.italicizeWithEmDash("Trapped!") + "You lose 3 life"));

        // 2-9 | Create five Treasure tokens.
        effect.addTableEntry(2, 9, new CreateTokenEffect(new TreasureToken(), 5));

        // 10-19 | You gain 3 life and draw three cards.
        effect.addTableEntry(
                10, 19, new GainLifeEffect(3),
                new DrawCardSourceControllerEffect(3).concatBy("and")
        );

        // 20 | Search your library for a card. If it's an artifact card you may put it onto the battlefield. Otherwise, put that card into your hand. Then shuffle.
        effect.addTableEntry(20, 20, new TreasureChestEffect());
        this.addAbility(ability);
    }

    private TreasureChest(final TreasureChest card) {
        super(card);
    }

    @Override
    public TreasureChest copy() {
        return new TreasureChest(this);
    }
}

class TreasureChestEffect extends OneShotEffect {

    TreasureChestEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a card. If it's an artifact card, you may " +
                "put it onto the battlefield. Otherwise, put that card into your hand. Then shuffle";
    }

    private TreasureChestEffect(final TreasureChestEffect effect) {
        super(effect);
    }

    @Override
    public TreasureChestEffect copy() {
        return new TreasureChestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary();
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        if (card == null) {
            player.shuffleLibrary(source, game);
            return true;
        }
        if (CardType.ARTIFACT.getPredicate().apply(card, game) && player.chooseUse(
                Outcome.PlayForFree, "Put it onto the battlefield or your hand?",
                null, "Battlefield", "Hand", source, game
        )) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        } else {
            player.moveCards(card, Zone.HAND, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
