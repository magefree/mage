package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DemonstrateAbility;
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
public final class IncarnationTechnique extends CardImpl {

    public IncarnationTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Demonstrate
        this.addAbility(new DemonstrateAbility());

        // Mill five cards, then return a creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new IncarnationTechniqueEffect());
    }

    private IncarnationTechnique(final IncarnationTechnique card) {
        super(card);
    }

    @Override
    public IncarnationTechnique copy() {
        return new IncarnationTechnique(this);
    }
}

class IncarnationTechniqueEffect extends OneShotEffect {

    IncarnationTechniqueEffect() {
        super(Outcome.Benefit);
        staticText = "mill five cards, then return a creature card from your graveyard to the battlefield";
    }

    private IncarnationTechniqueEffect(final IncarnationTechniqueEffect effect) {
        super(effect);
    }

    @Override
    public IncarnationTechniqueEffect copy() {
        return new IncarnationTechniqueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.millCards(5, source, game);
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        target.setNotTarget(true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return true;
        }
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}
