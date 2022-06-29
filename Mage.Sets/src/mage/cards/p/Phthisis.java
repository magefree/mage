
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class Phthisis extends CardImpl {

    public Phthisis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}{B}{B}");


        // Destroy target creature. Its controller loses life equal to its power plus its toughness.
        this.getSpellAbility().addEffect(new PhthisisEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Suspend 5-{1}{B}
        this.addAbility(new SuspendAbility(5, new ManaCostsImpl<>("{1}{B}"), this));
    }

    private Phthisis(final Phthisis card) {
        super(card);
    }

    @Override
    public Phthisis copy() {
        return new Phthisis(this);
    }
}

class PhthisisEffect extends OneShotEffect {

    public PhthisisEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature. Its controller loses life equal to its power plus its toughness";
    }

    public PhthisisEffect(final PhthisisEffect effect) {
        super(effect);
    }

    @Override
    public PhthisisEffect copy() {
        return new PhthisisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature != null) {
            Player controller = game.getPlayer(creature.getControllerId());
            if (controller != null) {
                int lifeLoss = CardUtil.overflowInc(creature.getPower().getValue(), creature.getToughness().getValue());
                creature.destroy(source, game, false);
                // the life loss happens also if the creature is indestructible or regenerated (legal targets)
                controller.loseLife(lifeLoss, game, source, false);
                return true;
            }
        }
        return false;
    }
}
