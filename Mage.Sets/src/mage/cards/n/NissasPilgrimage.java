
package mage.cards.n;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class NissasPilgrimage extends CardImpl {

    public NissasPilgrimage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Search your library for up to two basic Forest cards, reveal those cards, and put one onto the battlefield tapped and the rest into your hand.  Then shuffle your library.
        // <i>Spell Mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, search your library for up to three basic Forest cards instead of two.
        this.getSpellAbility().addEffect(new NissasPilgrimageEffect());
    }

    private NissasPilgrimage(final NissasPilgrimage card) {
        super(card);
    }

    @Override
    public NissasPilgrimage copy() {
        return new NissasPilgrimage(this);
    }
}

class NissasPilgrimageEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("basic Forest card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(SubType.FOREST.getPredicate());
    }

    public NissasPilgrimageEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for up to two basic Forest cards, reveal those cards, and put one onto the battlefield tapped and the rest into your hand. Then shuffle."
                + "<br><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, search your library for up to three basic Forest cards instead of two.";
    }

    public NissasPilgrimageEffect(final NissasPilgrimageEffect effect) {
        super(effect);
    }

    @Override
    public NissasPilgrimageEffect copy() {
        return new NissasPilgrimageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            int number = 2;
            if (SpellMasteryCondition.instance.apply(game, source)) {
                number++;
            }
            TargetCardInLibrary target = new TargetCardInLibrary(0, number, filter);
            controller.searchLibrary(target, source, game);
            if (!target.getTargets().isEmpty()) {
                Cards cards = new CardsImpl(target.getTargets());
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (!cards.isEmpty()) {
                    Card card = cards.getRandom(game);
                    if (card != null) {
                        cards.remove(card);
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                        controller.moveCards(cards, Zone.HAND, source, game);
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
