package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EssenceVortex extends CardImpl {

    public EssenceVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{B}");

        // Destroy target creature unless its controller pays life equal to its toughness. A creature destroyed this way can't be regenerated.
        this.getSpellAbility().addEffect(new EssenceVortexEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private EssenceVortex(final EssenceVortex card) {
        super(card);
    }

    @Override
    public EssenceVortex copy() {
        return new EssenceVortex(this);
    }
}

class EssenceVortexEffect extends OneShotEffect {

    EssenceVortexEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy target creature unless its controller pays life equal to its toughness. " +
                "A creature destroyed this way can't be regenerated.";
    }

    private EssenceVortexEffect(final EssenceVortexEffect effect) {
        super(effect);
    }

    @Override
    public EssenceVortexEffect copy() {
        return new EssenceVortexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Cost cost = new PayLifeCost(permanent.getToughness().getValue());
        if (cost.pay(source, game, source, permanent.getControllerId(), true)) {
            return true;
        }
        return permanent.destroy(source, game, true);
    }
}