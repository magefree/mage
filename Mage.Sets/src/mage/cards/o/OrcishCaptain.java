
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class OrcishCaptain extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Orc creature");

    static {
        filter.add(SubType.ORC.getPredicate());
    }

    public OrcishCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}: Flip a coin. If you win the flip, target Orc creature gets +2/+0 until end of turn. If you lose the flip, it gets -0/-2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OrcishCaptainEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private OrcishCaptain(final OrcishCaptain card) {
        super(card);
    }

    @Override
    public OrcishCaptain copy() {
        return new OrcishCaptain(this);
    }
}

class OrcishCaptainEffect extends OneShotEffect {

    public OrcishCaptainEffect() {
        super(Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, target Orc creature gets +2/+0 until end of turn. If you lose the flip, it gets -0/-2 until end of turn";
    }

    private OrcishCaptainEffect(final OrcishCaptainEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && permanent != null) {
            if (controller.flipCoin(source, game, true)) {
                game.informPlayers("Orcish Captain: Won flip. Target Orc creature gets +2/+0 until end of turn.");
                game.addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn), source);
                return true;
            } else {
                game.informPlayers("Orcish Captain: Lost flip. Target Orc creature gets -0/-2 until end of turn.");
                game.addEffect(new BoostTargetEffect(-0, -2, Duration.EndOfTurn), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public OrcishCaptainEffect copy() {
        return new OrcishCaptainEffect(this);
    }
}
