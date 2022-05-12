package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author spjspj
 */
public final class GoblinTutor extends CardImpl {

    public GoblinTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Roll a six-sided die. If you roll a 1, Goblin Tutor has no effect. Otherwise, search your library for the indicated card, reveal it, put it into your hand, then shuffle your library. 2 - A card named Goblin Tutor 3 - An enchantment card 4 - An artifact card 5 - A creature card 6 - An instant or sorcery card
        this.getSpellAbility().addEffect(new GoblinTutorEffect());
    }

    private GoblinTutor(final GoblinTutor card) {
        super(card);
    }

    @Override
    public GoblinTutor copy() {
        return new GoblinTutor(this);
    }
}

class GoblinTutorEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card named Goblin Tutor");

    static {
        filter.add(new NamePredicate("Goblin Tutor"));
    }

    public GoblinTutorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Roll a six-sided die. If you roll a 1, {this} has no effect. Otherwise, search your library for the indicated card, reveal it, put it into your hand, then shuffle." +
                "<br>2 - A card named Goblin Tutor" +
                "<br>3 - An enchantment" +
                "<br>4 - An artifact" +
                "<br>5 - A creature" +
                "<br>6 - An instant or sorcery";
    }

    public GoblinTutorEffect(final GoblinTutorEffect effect) {
        super(effect);
    }

    @Override
    public GoblinTutorEffect copy() {
        return new GoblinTutorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = controller.rollDice(outcome, source, game, 6);

            Effect effect = null;
            // 2 - A card named Goblin Tutor
            // 3 - An enchantment card
            // 4 - An artifact card
            // 5 - A creature card
            // 6 - An instant or sorcery card
            if (amount == 2) {
                effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, filter), true);
            } else if (amount == 3) {
                effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_ENTCHANTMENT), true);
            } else if (amount == 4) {
                effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_ARTIFACT), true);
            } else if (amount == 5) {
                effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_CREATURE), true);
            } else if (amount == 6) {
                effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, new FilterInstantOrSorceryCard()), true);
            }

            if (effect != null) {
                effect.apply(game, source);
                return true;
            }
        }
        return false;
    }
}
