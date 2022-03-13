package mage.cards.g;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class GatherThePack extends CardImpl {

    public GatherThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Reveal the top five cards of your library. You may put a creature card from among them into your hand. Put the rest into your graveyard.
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, put up to two creature cards from among the revealed cards into your hand instead of one.
        this.getSpellAbility().addEffect(new GatherThePackEffect());
    }

    private GatherThePack(final GatherThePack card) {
        super(card);
    }

    @Override
    public GatherThePack copy() {
        return new GatherThePack(this);
    }
}

class GatherThePackEffect extends OneShotEffect {

    public GatherThePackEffect(final GatherThePackEffect effect) {
        super(effect);
    }

    public GatherThePackEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Reveal the top five cards of your library. You may put a creature card from among them into your hand. Put the rest into your graveyard."
                + "<br><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, put up to two creature cards from among the revealed cards into your hand instead of one";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
        if (!cards.isEmpty()) {
            controller.revealCards(sourceObject.getIdName(), cards, game);
            int creatures = cards.count(StaticFilters.FILTER_CARD_CREATURE, source.getControllerId(), source, game);
            if (creatures > 0) {
                int max = 1;
                if (SpellMasteryCondition.instance.apply(game, source) && creatures > 1) {
                    max++;
                }
                TargetCard target = new TargetCard(0, max, Zone.LIBRARY, new FilterCreatureCard("creature card" + (max > 1 ? "s" : "") + " to put into your hand"));
                if (controller.choose(Outcome.PutCreatureInPlay, cards, target, game)) {
                    Cards cardsToHand = new CardsImpl(target.getTargets());
                    if (!cardsToHand.isEmpty()) {
                        cards.removeAll(cardsToHand);
                        controller.moveCards(cardsToHand, Zone.HAND, source, game);
                    }
                }
            }
            if (!cards.isEmpty()) {
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
        }
        return true;
    }

    @Override
    public GatherThePackEffect copy() {
        return new GatherThePackEffect(this);
    }
}
