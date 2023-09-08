
package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class RallyTheAncestors extends CardImpl {

    public RallyTheAncestors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{W}{W}");

        // Return each creature card with converted mana cost X or less from your graveyard to the battlefield.
        // Exile those creatures at the beginning of your next upkeep. Exile Rally the Ancestors.
        this.getSpellAbility().addEffect(new RallyTheAncestorsEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private RallyTheAncestors(final RallyTheAncestors card) {
        super(card);
    }

    @Override
    public RallyTheAncestors copy() {
        return new RallyTheAncestors(this);
    }
}

class RallyTheAncestorsEffect extends OneShotEffect {

    RallyTheAncestorsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return each creature card with mana value X or less from your graveyard to the battlefield. Exile those creatures at the beginning of your next upkeep";
    }

    private RallyTheAncestorsEffect(final RallyTheAncestorsEffect effect) {
        super(effect);
    }

    @Override
    public RallyTheAncestorsEffect copy() {
        return new RallyTheAncestorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int xValue = source.getManaCostsToPay().getX();
            FilterCreatureCard filter = new FilterCreatureCard();
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
            Set<Card> cards = player.getGraveyard().getCards(filter, game);
            player.moveCards(cards, Zone.BATTLEFIELD, source, game);
            List<Permanent> toExile = new ArrayList<>(cards.size());
            for (Card card : cards) {
                if (card != null) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        toExile.add(permanent);
                    }
                }
            }
            Effect exileEffect = new ExileTargetEffect("Exile those creatures at the beginning of your next upkeep");
            exileEffect.setTargetPointer(new FixedTargets(toExile, game));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(exileEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}
