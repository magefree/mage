package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellWithTimeCountersEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CharnelSerenade extends CardImpl {

    public CharnelSerenade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Surveil 3, then return a creature card from your graveyard to the battlefield with a finality counter on it. Exile Charnel Serenade with three time counters on it.
        this.getSpellAbility().addEffect(new SurveilEffect(3, false));
        this.getSpellAbility().addEffect(new CharnelSerenadeEffect());
        this.getSpellAbility().addEffect(new ExileSpellWithTimeCountersEffect(3));

        // Suspend 3--{2}{B}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{2}{B}"), this));

    }

    private CharnelSerenade(final CharnelSerenade card) {
        super(card);
    }

    @Override
    public CharnelSerenade copy() {
        return new CharnelSerenade(this);
    }
}

class CharnelSerenadeEffect extends OneShotEffect {

    CharnelSerenadeEffect() {
        super(Outcome.Benefit);
        staticText = ", then return a creature card from your graveyard to the battlefield with a finality counter on it";
    }

    private CharnelSerenadeEffect(final CharnelSerenadeEffect effect) {
        super(effect);
    }

    @Override
    public CharnelSerenadeEffect copy() {
        return new CharnelSerenadeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        game.setEnterWithCounters(card.getId(), new Counters().addCounter(CounterType.FINALITY.createInstance()));
        return player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
