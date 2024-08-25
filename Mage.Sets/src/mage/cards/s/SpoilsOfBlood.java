
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.SpoilsOfBloodHorrorToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SpoilsOfBlood extends CardImpl {

    public SpoilsOfBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.INSTANT }, "{B}");

        // Create an X/X black Horror creature token, where X is the number of creatures that died this turn.
        this.getSpellAbility().addEffect(new SpoilsOfBloodEffect());
    }

    private SpoilsOfBlood(final SpoilsOfBlood card) {
        super(card);
    }

    @Override
    public SpoilsOfBlood copy() {
        return new SpoilsOfBlood(this);
    }
}

class SpoilsOfBloodEffect extends OneShotEffect {

    SpoilsOfBloodEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Create an X/X black Horror creature token, where X is the number of creatures that died this turn";
    }

    private SpoilsOfBloodEffect(final SpoilsOfBloodEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            new CreateTokenEffect(
                    new SpoilsOfBloodHorrorToken(CreaturesDiedThisTurnCount.instance.calculate(game, source, this)))
                    .apply(game, source);
            return true;
        }
        return false;
    }

    @Override
    public SpoilsOfBloodEffect copy() {
        return new SpoilsOfBloodEffect(this);
    }

}