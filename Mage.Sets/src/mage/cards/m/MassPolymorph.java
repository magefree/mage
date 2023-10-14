
package mage.cards.m;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class MassPolymorph extends CardImpl {

    public MassPolymorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Exile all creatures you control, then reveal cards from the top of your library until you reveal that many creature cards.
        // Put all creature cards revealed this way onto the battlefield, then shuffle the rest of the revealed cards into your library.
        this.getSpellAbility().addEffect(new MassPolymorphEffect());
    }

    private MassPolymorph(final MassPolymorph card) {
        super(card);
    }

    @Override
    public MassPolymorph copy() {
        return new MassPolymorph(this);
    }
}

class MassPolymorphEffect extends OneShotEffect {

    public MassPolymorphEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Exile all creatures you control, then reveal cards from the top of your library until you reveal that many creature cards. Put all creature cards revealed this way onto the battlefield, then shuffle the rest of the revealed cards into your library";
    }

    private MassPolymorphEffect(final MassPolymorphEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Cards creatures = new CardsImpl();
            Set<Card> creaturesToExile = new HashSet<>(game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game));
            int count = creaturesToExile.size();
            controller.moveCards(creaturesToExile, Zone.EXILED, source, game);

            Cards revealed = new CardsImpl();
            Set<Card> creatureCards = new LinkedHashSet<>();
            for (Card card : controller.getLibrary().getCards(game)) {
                revealed.add(card);
                if (card.isCreature(game)) {
                    creatureCards.add(card);
                    if (creatureCards.size() == count) {
                        break;
                    }
                }
            }
            controller.revealCards(source, revealed, game);
            controller.moveCards(creatureCards, Zone.BATTLEFIELD, source, game, false, false, true, null);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public MassPolymorphEffect copy() {
        return new MassPolymorphEffect(this);
    }

}
