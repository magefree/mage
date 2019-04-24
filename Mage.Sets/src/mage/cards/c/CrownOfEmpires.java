
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author nantuko
 */
public final class CrownOfEmpires extends CardImpl {

    public CrownOfEmpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {3}, {tap}: Tap target creature. Gain control of that creature instead if you control artifacts named Scepter of Empires and Throne of Empires.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CrownOfEmpiresEffect(), new GenericManaCost(3));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public CrownOfEmpires(final CrownOfEmpires card) {
        super(card);
    }

    @Override
    public CrownOfEmpires copy() {
        return new CrownOfEmpires(this);
    }
}

class CrownOfEmpiresEffect extends OneShotEffect {

    public CrownOfEmpiresEffect() {
        super(Outcome.Tap);
        staticText = "Tap target creature. Gain control of that creature instead if you control artifacts named Scepter of Empires and Throne of Empires";
    }

    public CrownOfEmpiresEffect(CrownOfEmpiresEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
        boolean scepter = false;
        boolean throne = false;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.getName().equals("Scepter of Empires")) {
                scepter = true;
            } else if (permanent.getName().equals("Throne of Empires")) {
                throne = true;
            }
            if (scepter && throne) break;
        }
        if (scepter && throne) {
            ContinuousEffect effect = new CrownOfEmpiresControlEffect();
            effect.setTargetPointer(new FixedTarget(target.getId()));
            game.getState().setValue(source.getSourceId().toString(), source.getControllerId());
            game.addEffect(effect, source);
        } else {
            target.tap(game);
        }
        return false;
    }

    @Override
    public CrownOfEmpiresEffect copy() {
        return new CrownOfEmpiresEffect(this);
    }
}

class CrownOfEmpiresControlEffect extends ContinuousEffectImpl {

    public CrownOfEmpiresControlEffect() {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
    }

    public CrownOfEmpiresControlEffect(final CrownOfEmpiresControlEffect effect) {
        super(effect);
    }

    @Override
    public CrownOfEmpiresControlEffect copy() {
        return new CrownOfEmpiresControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        UUID controllerId = (UUID) game.getState().getValue(source.getSourceId().toString());
        if (permanent != null && controllerId != null) {
            return permanent.changeControllerId(controllerId, game);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Gain control of {this}";
    }
}
