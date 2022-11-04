package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class RazeToTheGround extends CardImpl {

    public RazeToTheGround(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility().setRuleAtTheTop(true));

        // Destroy target artifact. If its mana value was 1 or less, draw a card.
        this.getSpellAbility().addEffect(new RazeToTheGroundEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private RazeToTheGround(final RazeToTheGround card) {
        super(card);
    }

    @Override
    public RazeToTheGround copy() {
        return new RazeToTheGround(this);
    }
}

class RazeToTheGroundEffect extends OneShotEffect {

    public RazeToTheGroundEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact. If its mana value was 1 or less, draw a card.";
    }

    private RazeToTheGroundEffect(final RazeToTheGroundEffect effect) {
        super(effect);
    }

    @Override
    public RazeToTheGroundEffect copy() {
        return new RazeToTheGroundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int manaValue = permanent.getManaValue();
        permanent.destroy(source, game);
        if (manaValue <= 1) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.drawCards(1, source, game);
            }
        }
        return true;
    }
}
