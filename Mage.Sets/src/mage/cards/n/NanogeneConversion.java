package mage.cards.n;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class NanogeneConversion extends CardImpl {

    public NanogeneConversion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Choose target creature you control. Each other creature becomes a copy of that creature until end of turn, except it isn't legendary.
        this.getSpellAbility().addEffect(new NanogeneConversionEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private NanogeneConversion(final NanogeneConversion card) {
        super(card);
    }

    @Override
    public NanogeneConversion copy() {
        return new NanogeneConversion(this);
    }
}
//Based on Augmenter Pugilist's EchoingEquationEffect
//TODO: Refactor both into EachOtherBecomesCopyOfTargetEffect(FilterPermanent filter, Duration duration, CopyApplier applier)
class NanogeneConversionEffect extends OneShotEffect {

    NanogeneConversionEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature you control. Each other creature becomes a copy of that creature until end of turn, except it isn't legendary";
    }

    private NanogeneConversionEffect(final NanogeneConversionEffect effect) {
        super(effect);
    }

    @Override
    public NanogeneConversionEffect copy() {
        return new NanogeneConversionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent copyFrom = game.getPermanent(source.getFirstTarget());
        if (copyFrom != null) {
            game.getBattlefield().getActivePermanents(source.getControllerId(), game).stream()
                    .filter(permanent -> permanent.isCreature(game) && !permanent.getId().equals(copyFrom.getId()))
                    .forEach(copyTo -> game.copyPermanent(Duration.EndOfTurn, copyFrom, copyTo.getId(), source, new CopyApplier() {
                        @Override
                        public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
                            blueprint.removeSuperType(SuperType.LEGENDARY);
                            return true;
                        }
                    }));
            return true;
        }
        return false;
    }
}
