package mage.cards.c;

import mage.MageItem;
import mage.abilities.Ability;
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
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author nantuko
 */
public final class CrownOfEmpires extends CardImpl {

    public CrownOfEmpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {3}, {tap}: Tap target creature. Gain control of that creature instead if you control artifacts named Scepter of Empires and Throne of Empires.
        Ability ability = new SimpleActivatedAbility(new CrownOfEmpiresEffect(), new GenericManaCost(3));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CrownOfEmpires(final CrownOfEmpires card) {
        super(card);
    }

    @Override
    public CrownOfEmpires copy() {
        return new CrownOfEmpires(this);
    }
}

class CrownOfEmpiresEffect extends OneShotEffect {

    CrownOfEmpiresEffect() {
        super(Outcome.Tap);
        staticText = "Tap target creature. Gain control of that creature instead if you control artifacts named Scepter of Empires and Throne of Empires";
    }

    private CrownOfEmpiresEffect(final CrownOfEmpiresEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        boolean scepter = false;
        boolean throne = false;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (CardUtil.haveSameNames(permanent, "Scepter of Empires", game)) {
                scepter = true;
            } else if (CardUtil.haveSameNames(permanent, "Throne of Empires", game)) {
                throne = true;
            }
            if (scepter && throne) break;
        }
        if (scepter && throne) {
            ContinuousEffect effect = new CrownOfEmpiresControlEffect();
            effect.setTargetPointer(new FixedTarget(target.getId(), game));
            game.getState().setValue(source.getSourceId().toString(), source.getControllerId());
            game.addEffect(effect, source);
        } else {
            target.tap(source, game);
        }
        return false;
    }

    @Override
    public CrownOfEmpiresEffect copy() {
        return new CrownOfEmpiresEffect(this);
    }
}

class CrownOfEmpiresControlEffect extends ContinuousEffectImpl {

    CrownOfEmpiresControlEffect() {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "Gain control of {this}";
    }

    private CrownOfEmpiresControlEffect(final CrownOfEmpiresControlEffect effect) {
        super(effect);
    }

    @Override
    public CrownOfEmpiresControlEffect copy() {
        return new CrownOfEmpiresControlEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        UUID controllerId = (UUID) game.getState().getValue(source.getSourceId().toString());
        for (MageItem object : affectedObjects) {
            ((Permanent) object).changeControllerId(controllerId, game, source);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        UUID controllerId = (UUID) game.getState().getValue(source.getSourceId().toString());
        if (permanent != null && controllerId != null) {
            affectedObjects.add(permanent);
            return true;
        }
        return false;
    }
}
