
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HornetToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author FenrisulfrX
 */
public final class HornetCannon extends CardImpl {

    public HornetCannon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {3}, {tap}: Create a 1/1 colorless Insect artifact creature token with flying and haste named Hornet. Destroy it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HornetCannonEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private HornetCannon(final HornetCannon card) {
        super(card);
    }

    @Override
    public HornetCannon copy() {
        return new HornetCannon(this);
    }
}

class HornetCannonEffect extends OneShotEffect {

    public HornetCannonEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create a 1/1 colorless Insect artifact creature token with flying and haste named Hornet. Destroy it at the beginning of the next end step.";
    }

    private HornetCannonEffect(final HornetCannonEffect effect) {
        super(effect);
    }

    @Override
    public HornetCannonEffect copy() {
        return new HornetCannonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token hornetToken = new HornetToken();
        hornetToken.putOntoBattlefield(1, game, source, source.getControllerId());
        for (UUID tokenId : hornetToken.getLastAddedTokenIds()) {
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                DestroyTargetEffect destroyEffect = new DestroyTargetEffect(false);
                destroyEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(destroyEffect), source);
            }
        }
        return true;
    }
}
