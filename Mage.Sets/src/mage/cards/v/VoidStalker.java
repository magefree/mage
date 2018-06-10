
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class VoidStalker extends CardImpl {

    public VoidStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}{U}, {tap}: Put Void Stalker and target creature on top of their owners' libraries, then those players shuffle their libraries.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VoidStalkerEffect(), new ManaCostsImpl("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public VoidStalker(final VoidStalker card) {
        super(card);
    }

    @Override
    public VoidStalker copy() {
        return new VoidStalker(this);
    }
}

class VoidStalkerEffect extends OneShotEffect {
    VoidStalkerEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Put {this} and target creature on top of their owners' libraries, then those players shuffle their libraries";
    }

    VoidStalkerEffect(final VoidStalkerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent s = game.getPermanent(source.getSourceId());
        if (p != null) {
            p.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            Player pl = game.getPlayer(p.getControllerId());
            if (pl != null)
                pl.shuffleLibrary(source, game);
        }
        if (s != null) {
            s.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            Player pl = game.getPlayer(s.getControllerId());
            if (pl != null)
                pl.shuffleLibrary(source, game);
        }
        return true;
    }

    @Override
    public VoidStalkerEffect copy() {
        return new VoidStalkerEffect(this);
    }
}