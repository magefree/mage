package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class GrabTheReins extends CardImpl {

    public GrabTheReins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Until end of turn, you gain control of target creature and it gains haste;
        Effect effect = new GainControlTargetEffect(Duration.EndOfTurn);
        effect.setText("Until end of turn, you gain control of target creature");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and it gains haste");
        this.getSpellAbility().addEffect(effect);
        TargetCreaturePermanent target = new TargetCreaturePermanent();
        target.setTargetName("a creature to take control of");
        this.getSpellAbility().addTarget(target);
        // or sacrifice a creature, then Grab the Reins deals damage equal to that creature's power to any target.
        Mode mode = new Mode(new GrabTheReinsEffect());
        TargetAnyTarget target2 = new TargetAnyTarget();
        target2.setTargetName("a creature or player to damage");
        mode.addTarget(target2);
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {2}{R}
        this.addAbility(new EntwineAbility("{2}{R}"));
    }

    private GrabTheReins(final GrabTheReins card) {
        super(card);
    }

    @Override
    public GrabTheReins copy() {
        return new GrabTheReins(this);
    }
}

class GrabTheReinsEffect extends OneShotEffect {

    public GrabTheReinsEffect() {
        super(Outcome.Damage);
        staticText = "sacrifice a creature. {this} deals damage equal to that creature's power to any target";
    }

    public GrabTheReinsEffect(final GrabTheReinsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Target target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        target.setTargetName("a creature to sacrifice");
        if (!target.canChoose(controllerId, source, game)) {
            return false;
        }
        Player player = game.getPlayer(controllerId);
        if (player != null) {
            player.chooseTarget(Outcome.Sacrifice, target, source, game);
            Permanent creatureToSacrifice = game.getPermanent(target.getTargets().get(0));
            int amount = creatureToSacrifice.getPower().getValue();
            if (!creatureToSacrifice.sacrifice(source, game)) {
                return false;
            }
            if (amount > 0) {
                Permanent permanent = game.getPermanent(source.getFirstTarget());
                if (permanent != null) {
                    permanent.damage(amount, source.getSourceId(), source, game, false, true);
                    return true;
                }
                player = game.getPlayer(source.getFirstTarget());
                if (player != null) {
                    player.damage(amount, source.getSourceId(), source, game);
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public GrabTheReinsEffect copy() {
        return new GrabTheReinsEffect(this);
    }
}
