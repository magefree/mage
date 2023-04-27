package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonUndead extends CardImpl {

    public SummonUndead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // You may mill three cards. Then return a creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new SummonUndeadEffect());
    }

    private SummonUndead(final SummonUndead card) {
        super(card);
    }

    @Override
    public SummonUndead copy() {
        return new SummonUndead(this);
    }
}

class SummonUndeadEffect extends OneShotEffect {

    SummonUndeadEffect() {
        super(Outcome.Benefit);
        staticText = "you may mill three cards. Then return a creature card from your graveyard to the battlefield";
    }

    private SummonUndeadEffect(final SummonUndeadEffect effect) {
        super(effect);
    }

    @Override
    public SummonUndeadEffect copy() {
        return new SummonUndeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.chooseUse(outcome, "Mill three cards?", source, game)) {
            player.millCards(3, source, game);
        }
        if (player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) < 1) {
            return true;
        }
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        return true;
    }
}
