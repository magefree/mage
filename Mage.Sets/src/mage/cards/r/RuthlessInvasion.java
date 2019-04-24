

package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public final class RuthlessInvasion extends CardImpl {

    public RuthlessInvasion (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R/P}");

        this.getSpellAbility().addEffect(new RuthlessInvasionEffect());
    }

    public RuthlessInvasion (final RuthlessInvasion card) {
        super(card);
    }

    @Override
    public RuthlessInvasion copy() {
        return new RuthlessInvasion(this);
    }

}

class RuthlessInvasionEffect extends RestrictionEffect {
    RuthlessInvasionEffect() {
        super(Duration.EndOfTurn);
        staticText = "Nonartifact creatures can't block this turn";
    }

    RuthlessInvasionEffect(final RuthlessInvasionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (!permanent.isArtifact()) {
            return true;
        }
        return false;
    }

    @Override
    public RuthlessInvasionEffect copy() {
        return new RuthlessInvasionEffect(this);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

}