package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SquirrelToken;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class AcornCatapult extends CardImpl {

    public AcornCatapult(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {1}, {T}: Acorn Catapult deals 1 damage to any target. That creature's controller or that player creates a 1/1 green Squirrel creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new AcornCatapultEffect());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private AcornCatapult(final AcornCatapult card) {
        super(card);
    }

    @Override
    public AcornCatapult copy() {
        return new AcornCatapult(this);
    }
}

class AcornCatapultEffect extends OneShotEffect {

    public AcornCatapultEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "that creature's controller or that player creates a 1/1 green Squirrel creature token";
    }

    public AcornCatapultEffect(final AcornCatapultEffect effect) {
        super(effect);
    }

    @Override
    public AcornCatapultEffect copy() {
        return new AcornCatapultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
            if (permanent != null) {
                player = game.getPlayer(permanent.getControllerId());
            }
        }

        if (player != null) {
            new SquirrelToken().putOntoBattlefield(1, game, source, player.getId());
            return true;
        }

        return false;
    }
}
