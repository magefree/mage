package mage.cards.s;

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
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.target.common.TargetTriggeredAbility;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class StrionicResonator extends CardImpl {

    public StrionicResonator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}, {T}: Copy target triggered ability you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new StrionicResonatorEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetTriggeredAbility());
        this.addAbility(ability);
    }

    private StrionicResonator(final StrionicResonator card) {
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
        if (stackAbility == null) {
            return false;
        }
        stackAbility.createCopyOnStack(game, source, source.getControllerId(), true);
        return true;

    }

    @Override
    public StrionicResonatorEffect copy() {
        return new StrionicResonatorEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "Copy " + mode.getTargets().get(0).getTargetName() + ". You may choose new targets for the copy";
    }
}
