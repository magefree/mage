package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureRandomCreatureEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class VariableSolutions extends CardImpl {

    public VariableSolutions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");
        this.getSpellAbility().addEffect(new VariableSolutionsEffect());
    }

    private VariableSolutions(final VariableSolutions card) {
        super(card);
    }

    @Override
    public VariableSolutions copy() {
        return new VariableSolutions(this);
    }
}

class VariableSolutionsEffect extends OneShotEffect {

    VariableSolutionsEffect() {
        super(Outcome.Benefit);
        staticText = "if X is 1, seek a basic land card and put it onto the battlefield tapped. "
                + "<br>If X is 2, each opponent sacrifices an artifact of their choice. "
                + "<br>If X is 3, conjure a random creature card with mana value X onto the battlefield. "
                + "<br>If X is 4 or more, you gain 2 life. Then do all of the above";
    }

    private VariableSolutionsEffect(final VariableSolutionsEffect effect) {
        super(effect);
    }

    @Override
    public VariableSolutionsEffect copy() {
        return new VariableSolutionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int x = CardUtil.getSourceCostsTag(game, source, "X", 0);
        if (x >= 4) {
            controller.gainLife(2, game, source);
        }
        if (x == 1 || x >= 4) {
            List<Card> lands = controller.getLibrary().getCards(game).stream()
                    .filter(card -> StaticFilters.FILTER_CARD_BASIC_LAND.match(card, game))
                    .collect(Collectors.toList());
            Card land = RandomUtil.randomFromCollection(lands);
            if (land != null) {
                controller.moveCards(land, Zone.HAND, source, game);
                if (controller.getHand().contains(land.getId())) {
                    controller.moveCards(land, Zone.BATTLEFIELD, source, game, true, false, false, null);
                }
            }
        }
        if (x == 2 || x >= 4) {
            new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT).apply(game, source);
        }
        if (x >= 3) {
            ConjureRandomCreatureEffect.conjure(x, Zone.BATTLEFIELD, game, source);
        }
        return true;
    }
}
