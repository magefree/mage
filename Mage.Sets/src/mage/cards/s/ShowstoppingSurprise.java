package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShowstoppingSurprise extends CardImpl {

    public ShowstoppingSurprise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{R}");

        // Choose target creature you control. Turn it face up if it's face down. Then it deals damage equal to its power to each other creature.
        this.getSpellAbility().addEffect(new ShowstoppingSurpriseEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private ShowstoppingSurprise(final ShowstoppingSurprise card) {
        super(card);
    }

    @Override
    public ShowstoppingSurprise copy() {
        return new ShowstoppingSurprise(this);
    }
}

class ShowstoppingSurpriseEffect extends OneShotEffect {

    ShowstoppingSurpriseEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature you control. Turn it face up if it's face down. " +
                "Then it deals damage equal to its power to each other creature";
    }

    private ShowstoppingSurpriseEffect(final ShowstoppingSurpriseEffect effect) {
        super(effect);
    }

    @Override
    public ShowstoppingSurpriseEffect copy() {
        return new ShowstoppingSurpriseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        if (permanent.isFaceDown(game)) {
            permanent.turnFaceUp(source, game, source.getControllerId());
            game.applyEffects();
        }
        int power = permanent.getPower().getValue();
        if (power < 1) {
            return true;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (!creature.getId().equals(permanent.getId())) {
                creature.damage(power, permanent.getId(), source, game);
            }
        }
        return true;
    }
}
