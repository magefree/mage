package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.BargainAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BackForSeconds extends CardImpl {

    public BackForSeconds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Bargain
        this.addAbility(new BargainAbility());

        // Return up to two target creature cards from your graveyard to your hand. If this spell was bargained, you may put one of those cards with mana value 4 or less onto the battlefield instead of putting it into your hand.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new BackForSecondsEffect(),
                new ReturnToHandTargetEffect(),
                BargainedCondition.instance,
                "Return up to two target creature cards from your graveyard to your hand. "
                        + "If this spell was bargained, you may put one of those cards with mana value "
                        + "4 or less onto the battlefield instead of putting it into your hand."
        ));
        this.getSpellAbility().addTarget(
                new TargetCardInYourGraveyard(0, 2, StaticFilters.FILTER_CARD_CREATURE)
        );
    }

    private BackForSeconds(final BackForSeconds card) {
        super(card);
    }

    @Override
    public BackForSeconds copy() {
        return new BackForSeconds(this);
    }
}

class BackForSecondsEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 4));
    }

    BackForSecondsEffect() {
        super(Outcome.Benefit);
    }

    private BackForSecondsEffect(final BackForSecondsEffect effect) {
        super(effect);
    }

    @Override
    public BackForSecondsEffect copy() {
        return new BackForSecondsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Cards cards = new CardsImpl();
        for (Target target : source.getTargets()) {
            for (UUID id : target.getTargets()) {
                cards.add(id);
            }
        }
        cards.retainZone(Zone.GRAVEYARD, game);

        if (cards.count(filter, game) > 0) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(0, 1, filter, true);
            boolean chosen = controller.choose(Outcome.PutCreatureInPlay, cards, target, source, game);
            if (chosen) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    cards.remove(card);
                }
            }
        }

        controller.moveCardsToHandWithInfo(cards, source, game, true);
        return true;
    }

}