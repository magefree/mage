package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurningHands extends CardImpl {

    public BurningHands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Burning Hands deals 2 damage to target creature or planeswalker. If that permanent is green, Burning Hands deals 6 damage instead.
        this.getSpellAbility().addEffect(new BurningHandsEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private BurningHands(final BurningHands card) {
        super(card);
    }

    @Override
    public BurningHands copy() {
        return new BurningHands(this);
    }
}

class BurningHandsEffect extends OneShotEffect {

    BurningHandsEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 2 damage to target creature or planeswalker. " +
                "If that permanent is green, {this} deals 6 damage instead";
    }

    private BurningHandsEffect(final BurningHandsEffect effect) {
        super(effect);
    }

    @Override
    public BurningHandsEffect copy() {
        return new BurningHandsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return permanent.damage(permanent.getColor(game).isGreen() ? 6 : 2, source.getSourceId(), source, game) > 0;
    }
}
