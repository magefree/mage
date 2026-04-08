package mage.cards.f;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.common.PayVariableLifeCost;
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
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class FixWhatsBroken extends CardImpl {

    public FixWhatsBroken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{B}");

        // As an additional cost to cast this spell, pay X life.
        this.getSpellAbility().addCost(new PayVariableLifeCost(true));

        // Return each artifact and creature card with mana value X from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new FixWhatsBrokenEffect());
    }

    private FixWhatsBroken(final FixWhatsBroken card) {
        super(card);
    }

    @Override
    public FixWhatsBroken copy() {
        return new FixWhatsBroken(this);
    }
}

class FixWhatsBrokenEffect extends OneShotEffect {

    FixWhatsBrokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return each artifact and creature card with mana value X from your graveyard to the battlefield";
    }

    private FixWhatsBrokenEffect(final FixWhatsBrokenEffect effect) {
        super(effect);
    }

    @Override
    public FixWhatsBrokenEffect copy() {
        return new FixWhatsBrokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you == null) {
            return false;
        }
        int count = CardUtil.getSourceCostsTag(game, source, "X", 0);
        Set<Card> cards = you.getGraveyard().getCards(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE, game);
        cards.removeIf(Objects::isNull);
        cards.removeIf(card -> !card.isArtifact(game) && !card.isCreature(game));
        cards.removeIf(card -> card.getManaValue() != count);
        return you.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}
