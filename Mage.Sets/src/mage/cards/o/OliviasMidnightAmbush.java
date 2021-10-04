package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.condition.common.NightCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.NightHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OliviasMidnightAmbush extends CardImpl {

    public OliviasMidnightAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gets -2/-2 until end of turn. If it's night, that creature gets -13/-13 until end of turn instead.
        this.getSpellAbility().addEffect(new OliviasMidnightAmbushEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(NightHint.instance);
    }

    private OliviasMidnightAmbush(final OliviasMidnightAmbush card) {
        super(card);
    }

    @Override
    public OliviasMidnightAmbush copy() {
        return new OliviasMidnightAmbush(this);
    }
}

class OliviasMidnightAmbushEffect extends OneShotEffect {

    OliviasMidnightAmbushEffect() {
        super(Outcome.Benefit);
        staticText = "target creature gets -2/-2 until end of turn. " +
                "If it's night, that creature gets -13/-13 until end of turn instead";
    }

    private OliviasMidnightAmbushEffect(final OliviasMidnightAmbushEffect effect) {
        super(effect);
    }

    @Override
    public OliviasMidnightAmbushEffect copy() {
        return new OliviasMidnightAmbushEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int boost = NightCondition.instance.apply(game, source) ? -13 : -2;
        game.addEffect(new BoostTargetEffect(boost, boost), source);
        return true;
    }
}
