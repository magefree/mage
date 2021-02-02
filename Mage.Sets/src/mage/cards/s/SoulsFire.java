package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class SoulsFire extends CardImpl {

    public SoulsFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");


        // Target creature you control on the battlefield deals damage equal to its power to any target.
        this.getSpellAbility().addEffect(new SoulsFireEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private SoulsFire(final SoulsFire card) {
        super(card);
    }

    @Override
    public SoulsFire copy() {
        return new SoulsFire(this);
    }
}

class SoulsFireEffect extends OneShotEffect {

    public SoulsFireEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature you control deals damage equal to its power to any target";
    }

    public SoulsFireEffect(final SoulsFireEffect effect) {
        super(effect);
    }

    @Override
    public SoulsFireEffect copy() {
        return new SoulsFireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getFirstTarget());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (sourcePermanent == null) {
            return false;
        }

        UUID targetId = source.getTargets().get(1).getFirstTarget();
        int damage = sourcePermanent.getPower().getValue();

        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            permanent.damage(damage, sourcePermanent.getId(), source, game, false, true);
            return true;
        }

        Player player = game.getPlayer(targetId);
        if (player != null) {
            player.damage(damage, sourcePermanent.getId(), source, game);
            return true;
        }

        return false;
    }
}
