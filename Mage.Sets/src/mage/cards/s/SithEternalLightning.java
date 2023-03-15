package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Merlingilb
 */
public class SithEternalLightning extends CardImpl {
    public SithEternalLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        //This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        //Tap all creatures your opponents control. Those creatures don't untap during their controller's next untap step.
        this.getSpellAbility().addEffect(new SithEternalLightningEffect());

        //Scry 4.
        this.getSpellAbility().addEffect(new ScryEffect(4));
    }

    public SithEternalLightning(final SithEternalLightning card) {
        super(card);
    }

    @Override
    public SithEternalLightning copy() {
        return new SithEternalLightning(this);
    }
}

class SithEternalLightningEffect extends OneShotEffect {

    public SithEternalLightningEffect() {
        super(Outcome.Benefit);
        this.staticText = "Tap all creatures your opponents control. Those creatures don't untap during their controller's next untap step.";
    }

    public SithEternalLightningEffect(final SithEternalLightningEffect effect) {
        super(effect);
    }

    @Override
    public SithEternalLightningEffect copy() {
        return new SithEternalLightningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<UUID> opponents = game.getOpponents(controller.getId());
        if (controller != null && opponents != null) {
            for (UUID opponent : opponents) {
                List<Permanent> permanents = game.getBattlefield().getActivePermanents(opponent, game);
                for (Permanent permanent : permanents) {
                    if (permanent.isCreature() && permanent.getControllerId() == opponent) {
                        permanent.tap(source, game);
                        DontUntapInControllersNextUntapStepTargetEffect effect = new DontUntapInControllersNextUntapStepTargetEffect();
                        effect.setTargetPointer(new FixedTarget(permanent.getId(), game));
                        game.addEffect(effect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
