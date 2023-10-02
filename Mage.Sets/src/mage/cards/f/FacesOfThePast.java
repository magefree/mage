
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2 & L_J
 */
public final class FacesOfThePast extends CardImpl {

    public FacesOfThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // Whenever a creature dies, tap all untapped creatures that share a creature type with it or untap all tapped creatures that share a creature type with it.
        this.addAbility(new DiesCreatureTriggeredAbility(new FacesOfThePastEffect(), false, false, true));
    }

    private FacesOfThePast(final FacesOfThePast card) {
        super(card);
    }

    @Override
    public FacesOfThePast copy() {
        return new FacesOfThePast(this);
    }
}

class FacesOfThePastEffect extends OneShotEffect {

    public FacesOfThePastEffect() {
        super(Outcome.Benefit);
        this.staticText = "tap all untapped creatures that share a creature type with it or untap all tapped creatures that share a creature type with it";
    }

    private FacesOfThePastEffect(final FacesOfThePastEffect effect) {
        super(effect);
    }

    @Override
    public FacesOfThePastEffect copy() {
        return new FacesOfThePastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = (Permanent) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        if (targetPermanent != null) {
            Player controller = game.getPlayer(targetPermanent.getControllerId());
            if (controller != null) {
                if (controller.chooseUse(outcome, "Tap all untapped creatures that share a creature type with " + targetPermanent.getLogName() + "? (Otherwise, untaps all tapped)", source, game)) {
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, game)) {
                        if (!permanent.isTapped() && targetPermanent.shareCreatureTypes(game, permanent)) {
                            permanent.tap(source, game);
                        }
                    }
                } else {
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, game)) {
                        if (permanent.isTapped() && targetPermanent.shareCreatureTypes(game, permanent)) {
                            permanent.untap(game);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
