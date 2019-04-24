
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.common.TargetTriggeredAbility;

/**
 *
 * @author Plopman
 */
public final class StrionicResonator extends CardImpl {

    public StrionicResonator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}, {T}: Copy target triggered ability you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StrionicResonatorEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetTriggeredAbility());
        this.addAbility(ability);
    }

    public StrionicResonator(final StrionicResonator card) {
        super(card);
    }

    @Override
    public StrionicResonator copy() {
        return new StrionicResonator(this);
    }
}

class StrionicResonatorEffect extends OneShotEffect {

    public StrionicResonatorEffect() {
        super(Outcome.Copy);
    }

    public StrionicResonatorEffect(final StrionicResonatorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (stackAbility != null) {
            Player controller = game.getPlayer(source.getControllerId());
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (controller != null && sourcePermanent != null) {
                stackAbility.createCopyOnStack(game, source, source.getControllerId(), true);
                game.informPlayers(sourcePermanent.getIdName() + ": " + controller.getLogName() + " copied triggered ability");
                return true;
            }
        }
        return false;

    }

    @Override
    public StrionicResonatorEffect copy() {
        return new StrionicResonatorEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Copy ").append(mode.getTargets().get(0).getTargetName()).append(". You may choose new targets for the copy");
        return sb.toString();
    }
}
