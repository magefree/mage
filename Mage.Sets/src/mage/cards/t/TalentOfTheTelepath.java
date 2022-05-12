package mage.cards.t;

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
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TalentOfTheTelepath extends CardImpl {

    public TalentOfTheTelepath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Target opponent reveals the top seven cards of their library. 
        // You may cast an instant or sorcery card from among them without paying 
        // its mana cost. Then that player puts the rest into their graveyard.
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or 
        // sorcery cards in your graveyard, you may cast up to two revealed instant 
        // and/or sorcery cards instead of one.
        this.getSpellAbility().addEffect(new TalentOfTheTelepathEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private TalentOfTheTelepath(final TalentOfTheTelepath card) {
        super(card);
    }

    @Override
    public TalentOfTheTelepath copy() {
        return new TalentOfTheTelepath(this);
    }
}

class TalentOfTheTelepathEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    public TalentOfTheTelepathEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Target opponent reveals the top seven cards of their " +
                "library. You may cast an instant or sorcery spell from among them " +
                "without paying its mana cost. Then that player puts the rest into their graveyard. "
                + "<br><i>Spell mastery</i> &mdash; If there are two or more instant " +
                "and/or sorcery cards in your graveyard, you may cast up to two " +
                "instant and/or sorcery spells from among the revealed cards instead of one.";
    }

    public TalentOfTheTelepathEffect(final TalentOfTheTelepathEffect effect) {
        super(effect);
    }

    @Override
    public TalentOfTheTelepathEffect copy() {
        return new TalentOfTheTelepathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, 7));
        opponent.revealCards(source, cards, game);
        CardUtil.castMultipleWithAttributeForFree(
                controller, source, game, cards, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY,
                SpellMasteryCondition.instance.apply(game, source) ? 2 : 1
        );
        cards.retainZone(Zone.LIBRARY, game);
        opponent.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}
