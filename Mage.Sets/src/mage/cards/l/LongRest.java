package mage.cards.l;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author weirddan455
 */
public final class LongRest extends CardImpl {

    public LongRest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}{G}");

        // Return X target cards with different mana values from your graveyard to your hand.
        // If eight or more cards were returned to your hand this way, your life total becomes equal to your starting life total.
        // Exile Long Rest.
        this.getSpellAbility().setTargetAdjuster(LongRestAdjuster.instance);
        this.getSpellAbility().addEffect(new LongRestEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private LongRest(final LongRest card) {
        super(card);
    }

    @Override
    public LongRest copy() {
        return new LongRest(this);
    }
}

class LongRestTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter = new FilterCard("cards with different mana values from your graveyard");

    public LongRestTarget(int xValue) {
        super(xValue, filter);
    }

    private LongRestTarget(final LongRestTarget target) {
        super(target);
    }

    @Override
    public LongRestTarget copy() {
        return new LongRestTarget(this);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        Set<Integer> manaValues = new HashSet<>();
        for (UUID targetId : this.getTargets()) {
            Card card = game.getCard(targetId);
            if (card != null) {
                manaValues.add(card.getManaValue());
            }
        }
        for (UUID possibleTargetId : super.possibleTargets(sourceControllerId, source, game)) {
            Card card = game.getCard(possibleTargetId);
            if (card != null && !manaValues.contains(card.getManaValue())) {
                possibleTargets.add(possibleTargetId);
            }
        }
        return possibleTargets;
    }
}

enum LongRestAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new LongRestTarget(ability.getManaCostsToPay().getX()));
    }
}

class LongRestEffect extends OneShotEffect {

    public LongRestEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return X target cards with different mana values from your graveyard to your hand. "
                + "If eight or more cards were returned to your hand this way, your life total becomes equal to your starting life total";
    }

    private LongRestEffect(final LongRestEffect effect) {
        super(effect);
    }

    @Override
    public LongRestEffect copy() {
        return new LongRestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cardsToHand = new HashSet<>();
            for (UUID targetId : targetPointer.getTargets(game, source)) {
                Card card = game.getCard(targetId);
                if (card != null && game.getState().getZone(targetId) == Zone.GRAVEYARD) {
                    cardsToHand.add(card);
                }
            }
            int numCards = cardsToHand.size();
            if (numCards > 0) {
                controller.moveCards(cardsToHand, Zone.HAND, source, game);
                if (numCards >= 8) {
                    controller.setLife(game.getStartingLife(), game, source);
                }
                return true;
            }
        }
        return false;
    }
}
