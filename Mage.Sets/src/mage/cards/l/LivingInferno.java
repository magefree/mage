
package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2 & L_J
 */
public final class LivingInferno extends CardImpl {

    public LivingInferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(8);
        this.toughness = new MageInt(5);

        // {T}: Living Inferno deals damage equal to its power divided as you choose among any number of target creatures. Each of those creatures deals damage equal to its power to Living Inferno.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LivingInfernoEffect(), new TapSourceCost());
        ability.setTargetAdjuster(LivingInfernoAdjuster.instance);
        this.addAbility(ability);
    }

    private LivingInferno(final LivingInferno card) {
        super(card);
    }

    @Override
    public LivingInferno copy() {
        return new LivingInferno(this);
    }
}

enum LivingInfernoAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(ability.getSourceId());
        if (sourcePermanent != null) {
            ability.getTargets().clear();
            ability.addTarget(new TargetCreaturePermanentAmount(sourcePermanent.getPower().getValue()));
        }
    }
}

class LivingInfernoEffect extends OneShotEffect {

    public LivingInfernoEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals damage equal to its power " +
                "divided as you choose among any number of target creatures. " +
                "Each of those creatures deals damage equal to its power to {this}";
    }

    private LivingInfernoEffect(final LivingInfernoEffect effect) {
        super(effect);
    }

    @Override
    public LivingInfernoEffect copy() {
        return new LivingInfernoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            Target multiTarget = source.getTargets().get(0);
            Set<Permanent> permanents = new HashSet<>();
            for (UUID target : multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanents.add(permanent);
                    permanent.damage(multiTarget.getTargetAmount(target), source.getSourceId(), source, game, false, true);
                }
            }
            // Each of those creatures deals damage equal to its power to Living Inferno
            Permanent sourceCreature = game.getPermanent(source.getSourceId());
            if (sourceCreature != null) {
                for (Permanent permanent : permanents) {
                    sourceCreature.damage(permanent.getPower().getValue(), permanent.getId(), source, game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
