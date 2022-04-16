package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class ForTheFamily extends CardImpl {

    public ForTheFamily(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature gets +2/+2 until end of turn. If you control four or more creatures, that creature gets +4/+4 until end of turn instead.
        this.getSpellAbility().addEffect(new ForTheFamilyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ForTheFamily(final ForTheFamily card) {
        super(card);
    }

    @Override
    public ForTheFamily copy() {
        return new ForTheFamily(this);
    }
}

class ForTheFamilyEffect extends OneShotEffect {

    public ForTheFamilyEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Target creature gets +2/+2 until end of turn. If you control four or more creatures, that creature gets +4/+4 until end of turn instead";
    }

    private ForTheFamilyEffect(final ForTheFamilyEffect effect) {
        super(effect);
    }

    @Override
    public ForTheFamilyEffect copy() {
        return new ForTheFamilyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int boost;
        if (game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game) >= 4) {
            boost = 4;
        } else {
            boost = 2;
        }
        game.addEffect(new BoostTargetEffect(boost, boost), source);
        return true;
    }
}
