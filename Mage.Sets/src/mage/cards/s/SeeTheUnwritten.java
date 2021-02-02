package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.FerociousHint;
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
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SeeTheUnwritten extends CardImpl {

    public SeeTheUnwritten(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Reveal the top eight cards of your library. You may put a creature card from among them onto the battlefield. Put the rest into your graveyard.
        // <i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, you may put two creature cards onto the battlefield instead of one.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SeeTheUnwrittenEffect(1),
                new SeeTheUnwrittenEffect(2),
                new InvertCondition(FerociousCondition.instance),
                "Reveal the top eight cards of your library. You may put a creature card from among them onto the battlefield. Put the rest into your graveyard."
                        + "<br/><i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, you may put two creature cards onto the battlefield instead of one"));
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private SeeTheUnwritten(final SeeTheUnwritten card) {
        super(card);
    }

    @Override
    public SeeTheUnwritten copy() {
        return new SeeTheUnwritten(this);
    }
}

class SeeTheUnwrittenEffect extends OneShotEffect {

    private final int numberOfCardsToPutIntoPlay;

    public SeeTheUnwrittenEffect(int numberOfCardsToPutIntoPlay) {
        super(Outcome.DrawCard);
        this.numberOfCardsToPutIntoPlay = numberOfCardsToPutIntoPlay;
        this.staticText = "Reveal the top eight cards of your library. You may put "
                + (numberOfCardsToPutIntoPlay == 1 ? "a creature card" : "two creature cards")
                + " from among them onto the battlefield. Put the rest into your graveyard";
    }

    public SeeTheUnwrittenEffect(final SeeTheUnwrittenEffect effect) {
        super(effect);
        this.numberOfCardsToPutIntoPlay = effect.numberOfCardsToPutIntoPlay;
    }

    @Override
    public SeeTheUnwrittenEffect copy() {
        return new SeeTheUnwrittenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 8));
            int creatureCardsFound = cards.count(StaticFilters.FILTER_CARD_CREATURE, game);
            if (!cards.isEmpty()) {
                controller.revealCards(source, cards, game);
                if (creatureCardsFound > 0 && controller.chooseUse(outcome, "Put creature(s) into play?", source, game)) {
                    int cardsToChoose = Math.min(numberOfCardsToPutIntoPlay, creatureCardsFound);
                    TargetCard target = new TargetCard(cardsToChoose, cardsToChoose, Zone.LIBRARY, StaticFilters.FILTER_CARD_CREATURE);
                    if (controller.choose(Outcome.PutCreatureInPlay, cards, target, game)) {
                        Cards toBattlefield = new CardsImpl(target.getTargets());
                        controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
                        cards.removeAll(toBattlefield);
                    }
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }

}
