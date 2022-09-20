package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetActivatedOrTriggeredAbility;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LithoformEngine extends CardImpl {

    private static final FilterStackObject filter
            = new FilterStackObject("activated or triggered ability you control");
    private static final FilterSpell filter2
            = new FilterInstantOrSorcerySpell("instant or sorcery spell you control");
    private static final FilterSpell filter3
            = new FilterSpell("permanent spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(TargetController.YOU.getControllerPredicate());
        filter3.add(TargetController.YOU.getControllerPredicate());
        filter3.add(PermanentPredicate.instance);
    }

    public LithoformEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.addSuperType(SuperType.LEGENDARY);

        // {2}, {T}: Copy target activated or triggered ability you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new LithoformEngineEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetActivatedOrTriggeredAbility(filter));
        this.addAbility(ability);

        // {3}, {T}: Copy target instant or sorcery spell you control. You may choose new targets for the copy.
        ability = new SimpleActivatedAbility(new CopyTargetSpellEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetSpell(filter2));
        this.addAbility(ability);

        // {4}, {T}: Copy target permanent spell you control.
        ability = new SimpleActivatedAbility(new CopyTargetSpellEffect(
                false, false, false
        ), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetSpell(filter3));
        this.addAbility(ability);
    }

    private LithoformEngine(final LithoformEngine card) {
        super(card);
    }

    @Override
    public LithoformEngine copy() {
        return new LithoformEngine(this);
    }
}

class LithoformEngineEffect extends OneShotEffect {

    public LithoformEngineEffect() {
        super(Outcome.Copy);
        this.staticText = "Copy target activated or triggered ability you control. You may choose new targets for the copy";
    }

    public LithoformEngineEffect(final LithoformEngineEffect effect) {
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
                return true;
            }
        }
        return false;

    }

    @Override
    public LithoformEngineEffect copy() {
        return new LithoformEngineEffect(this);
    }
}
