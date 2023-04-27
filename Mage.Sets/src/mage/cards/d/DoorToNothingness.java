
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class DoorToNothingness extends CardImpl {

    public DoorToNothingness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Door to Nothingness enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // {W}{W}{U}{U}{B}{B}{R}{R}{G}{G}, {tap}, Sacrifice Door to Nothingness: Target player loses the game.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DoorToNothingnessEffect(), new ManaCostsImpl<>("{W}{W}{U}{U}{B}{B}{R}{R}{G}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DoorToNothingness(final DoorToNothingness card) {
        super(card);
    }

    @Override
    public DoorToNothingness copy() {
        return new DoorToNothingness(this);
    }
}

class DoorToNothingnessEffect extends OneShotEffect {

    public DoorToNothingnessEffect() {
        super(Outcome.Damage);
        this.staticText = "Target player loses the game";
    }

    public DoorToNothingnessEffect(final DoorToNothingnessEffect effect) {
        super(effect);
    }

    @Override
    public DoorToNothingnessEffect copy() {
        return new DoorToNothingnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player target = game.getPlayer(source.getFirstTarget());
        if (target != null) {
            target.lost(game);
            return true;
        }
        return false;
    }
}
