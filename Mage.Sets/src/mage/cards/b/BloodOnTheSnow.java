package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class BloodOnTheSnow extends CardImpl {

    public BloodOnTheSnow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        this.supertype.add(SuperType.SNOW);

        // Choose one —
        // • Destroy all creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));

        // • Destroy all planeswalkers.
        Mode mode = new Mode(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_PLANESWALKERS));

        // Then return a creature or planeswalker card with converted mana cost X or less
        // from your graveyard to the battlefield, where X is the amount of {S} spent to cast this spell.
        this.getSpellAbility().addEffect(new BloodOnTheSnowEffect());
        mode.addEffect(new BloodOnTheSnowEffect());
        this.getSpellAbility().addMode(mode);
        this.getSpellAbility().appendToRule(
                "Then return a creature or planeswalker card with mana value X or less"
                        + " from your graveyard to the battlefield, where X is the amount of {S} spent to cast this spell."
        );
    }

    private BloodOnTheSnow(final BloodOnTheSnow card) {
        super(card);
    }

    @Override
    public BloodOnTheSnow copy() {
        return new BloodOnTheSnow(this);
    }
}

class BloodOnTheSnowEffect extends OneShotEffect {

    public BloodOnTheSnowEffect() {
        super(Outcome.PutCardInPlay);
    }

    private BloodOnTheSnowEffect(final BloodOnTheSnowEffect effect) {
        super(effect);
    }

    @Override
    public BloodOnTheSnowEffect copy() {
        return new BloodOnTheSnowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int snow = ManaPaidSourceWatcher.getSnowPaid(source.getId(), game);
            FilterCard filter = new FilterCard("a creature or planeswalker card with mana value " + snow + " or less from your graveyard");
            filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.PLANESWALKER.getPredicate()));
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, snow + 1));
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(1, 1, filter, true);
            controller.chooseTarget(outcome, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                return true;
            }
        }
        return false;
    }
}
