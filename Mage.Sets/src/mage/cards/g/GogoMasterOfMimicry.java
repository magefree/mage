package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.common.TargetActivatedOrTriggeredAbility;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GogoMasterOfMimicry extends CardImpl {

    private static final FilterStackObject filter
            = new FilterActivatedOrTriggeredAbility("activated or triggered ability you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public GogoMasterOfMimicry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {X}{X}, {T}: Copy target activated or triggered ability you control X times. You may choose new targets for the copy. This ability can't be copied, and X can't be 0.
        Ability ability = new SimpleActivatedAbility(new GogoMasterOfMimicryCopyEffect(), new ManaCostsImpl<>("{X}{X}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetActivatedOrTriggeredAbility(filter));
        CardUtil.castStream(ability.getCosts(), VariableManaCost.class).forEach(cost -> cost.setMinX(1));
        this.addAbility(ability.withCanBeCopied(false));
    }

    private GogoMasterOfMimicry(final GogoMasterOfMimicry card) {
        super(card);
    }

    @Override
    public GogoMasterOfMimicry copy() {
        return new GogoMasterOfMimicry(this);
    }
}

class GogoMasterOfMimicryCopyEffect extends OneShotEffect {

    GogoMasterOfMimicryCopyEffect() {
        super(Outcome.Benefit);
        staticText = "copy target activated or triggered ability you control X times. " +
                "You may choose new targets for the copies. This ability can't be copied and X can't be 0";
    }

    private GogoMasterOfMimicryCopyEffect(final GogoMasterOfMimicryCopyEffect effect) {
        super(effect);
    }

    @Override
    public GogoMasterOfMimicryCopyEffect copy() {
        return new GogoMasterOfMimicryCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = GetXValue.instance.calculate(game, source, this);
        StackObject stackObject = game.getStack().getStackObject(getTargetPointer().getFirst(game, source));
        if (amount < 1 || stackObject == null) {
            return false;
        }
        stackObject.createCopyOnStack(game, source, source.getControllerId(), true, amount);
        return true;
    }
}
