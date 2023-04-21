package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonsterManual extends AdventureCard {

    public MonsterManual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, new CardType[]{CardType.SORCERY}, "{3}{G}", "Zoological Study", "{2}{G}");

        // {1}{G}, {T}: You may put a creature card from your hand onto the battlefield.
        Ability ability = new SimpleActivatedAbility(
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_A),
                new ManaCostsImpl<>("{1}{G}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Zoological Study
        // Mill five cards, then return a creature card milled this way to your hand.
        this.getSpellCard().getSpellAbility().addEffect(new ZoologicalStudyEffect());
    }

    private MonsterManual(final MonsterManual card) {
        super(card);
    }

    @Override
    public MonsterManual copy() {
        return new MonsterManual(this);
    }
}

class ZoologicalStudyEffect extends OneShotEffect {

    ZoologicalStudyEffect() {
        super(Outcome.Benefit);
        staticText = "mill five cards, then return a creature card milled this way to your hand";
    }

    private ZoologicalStudyEffect(final ZoologicalStudyEffect effect) {
        super(effect);
    }

    @Override
    public ZoologicalStudyEffect copy() {
        return new ZoologicalStudyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player
                .millCards(5, source, game)
                .getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        Card card;
        switch (cards.size()) {
            case 0:
                return true;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCard(Zone.ALL, StaticFilters.FILTER_CARD_CREATURE);
                player.choose(outcome, cards, target, source, game);
                card = cards.get(target.getFirstTarget(), game);
        }
        player.moveCards(card, Zone.HAND, source, game);
        return true;
    }
}
