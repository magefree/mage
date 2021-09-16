package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ClueArtifactToken;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author weirddan455
 */
public final class FatefulAbsence extends CardImpl {

    public FatefulAbsence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Destroy target creature or planeswalker. Its controller investigates.
        this.getSpellAbility().addEffect(new FatefulAbsenceEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private FatefulAbsence(final FatefulAbsence card) {
        super(card);
    }

    @Override
    public FatefulAbsence copy() {
        return new FatefulAbsence(this);
    }
}

class FatefulAbsenceEffect extends OneShotEffect {

    public FatefulAbsenceEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target creature or planeswalker. Its controller investigates";
    }

    private FatefulAbsenceEffect(final FatefulAbsenceEffect effect) {
        super(effect);
    }

    @Override
    public FatefulAbsenceEffect copy() {
        return new FatefulAbsenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        UUID controllerId = permanent.getControllerId();
        permanent.destroy(source, game, false);
        new ClueArtifactToken().putOntoBattlefield(1, game, source, controllerId);
        return true;
    }
}
