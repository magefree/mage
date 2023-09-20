
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EpicEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class EternalDominion extends CardImpl {

    public EternalDominion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{U}{U}{U}");

        // Search target opponent's library for an artifact, creature, enchantment, or land card.
        // Put that card onto the battlefield under your control. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new EternalDominionEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Epic (For the rest of the game, you can't cast spells. At the beginning of each of your upkeeps
        // for the rest of the game, copy this spell except for its epic ability. If the spell has targets, you may choose new targets for the copy)
        this.getSpellAbility().addEffect(new EpicEffect());

    }

    private EternalDominion(final EternalDominion card) {
        super(card);
    }

    @Override
    public EternalDominion copy() {
        return new EternalDominion(this);
    }
}

class EternalDominionEffect extends OneShotEffect {

    private static final FilterCard FILTER = new FilterCard("an artifact, creature, enchantment, or land card");

    static {
        FILTER.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public EternalDominionEffect() {
        super(Outcome.Benefit);
        staticText = "Search target opponent's library for an artifact, creature, enchantment, or land card. Put that card onto the battlefield under your control. Then that player shuffles";
    }

    private EternalDominionEffect(final EternalDominionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent != null && controller != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(FILTER);
            controller.searchLibrary(target, source, game, opponent.getId());
            Card targetCard = game.getCard(target.getFirstTarget());
            if (targetCard != null) {
                applied = controller.moveCards(targetCard, Zone.BATTLEFIELD, source, game);
            }
            opponent.shuffleLibrary(source, game);
        }
        return applied;
    }

    @Override
    public EternalDominionEffect copy() {
        return new EternalDominionEffect(this);
    }
}
