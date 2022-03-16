
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author spjspj
 */
public final class CleansingMeditation extends CardImpl {

    public CleansingMeditation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}{W}");
        // Destroy all enchantments.
        // Threshold - If seven or more cards are in your graveyard, instead destroy all enchantments, then return all cards in your graveyard destroyed this way to the battlefield
        this.getSpellAbility().addEffect(new CleansingMeditationEffect());
    }

    private CleansingMeditation(final CleansingMeditation card) {
        super(card);
    }

    @Override
    public CleansingMeditation copy() {
        return new CleansingMeditation(this);
    }
}

class CleansingMeditationEffect extends OneShotEffect {

    public CleansingMeditationEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all enchantments.<br>" + AbilityWord.THRESHOLD.formatWord() + "If seven or more cards are in your graveyard, instead destroy all enchantments, then return all cards in your graveyard destroyed this way to the battlefield.";
    }

    public CleansingMeditationEffect(final CleansingMeditationEffect effect) {
        super(effect);
    }

    @Override
    public CleansingMeditationEffect copy() {
        return new CleansingMeditationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cardsToBattlefield = new CardsImpl();

        // Threshold?
        boolean threshold = false;
        DynamicValue c = new CardsInControllerGraveyardCount();
        int numCards = c.calculate(game, source, this);
        if (numCards >= 7) {
            threshold = true;
        }

        Player controller = game.getPlayer(source.getControllerId());

        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_ENCHANTMENT, source.getControllerId(), source.getSourceId(), game)) {
            if (permanent != null && permanent.destroy(source, game, false)) {
                if (threshold && controller != null && permanent.isOwnedBy(controller.getId())) {
                    cardsToBattlefield.add(permanent);
                }
            }
        }

        if (threshold && controller != null) {
            controller.moveCards(cardsToBattlefield, Zone.BATTLEFIELD, source, game);
        }

        return true;
    }
}
