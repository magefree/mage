package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ClueArtifactToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoldOut extends CardImpl {

    public SoldOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Exile target creature. If it was dealt damage this turn, create a Clue token.
        this.getSpellAbility().addEffect(new SoldOutEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SoldOut(final SoldOut card) {
        super(card);
    }

    @Override
    public SoldOut copy() {
        return new SoldOut(this);
    }
}

class SoldOutEffect extends OneShotEffect {

    SoldOutEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature. If it was dealt damage this turn, create a Clue token";
    }

    private SoldOutEffect(final SoldOutEffect effect) {
        super(effect);
    }

    @Override
    public SoldOutEffect copy() {
        return new SoldOutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        boolean flag = permanent.getDamage() > 0;
        Optional.ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .ifPresent(player -> player.moveCards(permanent, Zone.EXILED, source, game));
        if (flag) {
            new ClueArtifactToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
