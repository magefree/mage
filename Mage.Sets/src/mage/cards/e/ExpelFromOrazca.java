package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.AscendEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.hint.common.PermanentsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.designations.DesignationType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ExpelFromOrazca extends CardImpl {

    public ExpelFromOrazca(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Ascend (If you control ten or more permanents, you get the city’s blessing for the rest of the game.)
        this.getSpellAbility().addEffect(new AscendEffect());
        this.getSpellAbility().addHint(CitysBlessingHint.instance);
        this.getSpellAbility().addHint(PermanentsYouControlHint.instance);

        // Return target nonland permanent to its owner's hand. If you have the city's blessing, you may put that permanent on top of its owner's library instead.
        this.getSpellAbility().addEffect(new ExpelFromOrazcaEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private ExpelFromOrazca(final ExpelFromOrazca card) {
        super(card);
    }

    @Override
    public ExpelFromOrazca copy() {
        return new ExpelFromOrazca(this);
    }
}

class ExpelFromOrazcaEffect extends OneShotEffect {

    public ExpelFromOrazcaEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target nonland permanent to its owner's hand. If you have the city's blessing, you may put that permanent on top of its owner's library instead";
    }

    private ExpelFromOrazcaEffect(final ExpelFromOrazcaEffect effect) {
        super(effect);
    }

    @Override
    public ExpelFromOrazcaEffect copy() {
        return new ExpelFromOrazcaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetPermanent != null) {
                if (controller.hasDesignation(DesignationType.CITYS_BLESSING)
                        && controller.chooseUse(outcome, "Put " + targetPermanent.getIdName() + " on top of its owner's library instead?", source, game)) {
                    controller.moveCards(targetPermanent, Zone.LIBRARY, source, game);
                } else {
                    controller.moveCards(targetPermanent, Zone.HAND, source, game);
                }

            }
            return true;
        }
        return false;
    }
}
