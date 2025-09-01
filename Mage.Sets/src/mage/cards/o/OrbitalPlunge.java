package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.LanderToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OrbitalPlunge extends CardImpl {

    public OrbitalPlunge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Orbital Plunge deals 6 damage to target creature. If excess damage was dealt to a permanent this way, create a Lander token.
        this.getSpellAbility().addEffect(new OrbitalPlungeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private OrbitalPlunge(final OrbitalPlunge card) {
        super(card);
    }

    @Override
    public OrbitalPlunge copy() {
        return new OrbitalPlunge(this);
    }
}

// Inspired by Bottle-Cap Blast
class OrbitalPlungeEffect extends OneShotEffect {

    OrbitalPlungeEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 6 damage to target creature. " +
                "If excess damage was dealt this way, create a Lander token.";
    }

    private OrbitalPlungeEffect(final OrbitalPlungeEffect effect) {
        super(effect);
    }

    @Override
    public OrbitalPlungeEffect copy() {
        return new OrbitalPlungeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        if (permanent.damageWithExcess(6, source, game) > 0) {
            new LanderToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
