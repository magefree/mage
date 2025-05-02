package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BulkUp extends CardImpl {

    public BulkUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Double target creature's power until end of turn.
        this.getSpellAbility().addEffect(new BulkUpEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Flashback {4}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{R}{R}")));
    }

    private BulkUp(final BulkUp card) {
        super(card);
    }

    @Override
    public BulkUp copy() {
        return new BulkUp(this);
    }
}

class BulkUpEffect extends OneShotEffect {

    BulkUpEffect() {
        super(Outcome.Benefit);
        staticText = "double target creature's power until end of turn";
    }

    private BulkUpEffect(final BulkUpEffect effect) {
        super(effect);
    }

    @Override
    public BulkUpEffect copy() {
        return new BulkUpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || permanent.getPower().getValue() == 0) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(
                permanent.getPower().getValue(), 0
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
