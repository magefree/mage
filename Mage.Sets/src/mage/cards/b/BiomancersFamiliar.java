package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BiomancersFamiliar extends CardImpl {

    public BiomancersFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Activated abilities of creatures you control cost {2} less to activate. 
        // This effect can't reduce the amount of mana an ability costs to activate to less than one mana.
        this.addAbility(new SimpleStaticAbility(new BiomancersFamiliarCostReductionEffect()));

        // {T}: The next time target creature adapts this turn, it adapts as though it had no +1/+1 counters on it.
        Ability ability = new SimpleActivatedAbility(new BiomancersFamiliarReplacementEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BiomancersFamiliar(final BiomancersFamiliar card) {
        super(card);
    }

    @Override
    public BiomancersFamiliar copy() {
        return new BiomancersFamiliar(this);
    }
}

class BiomancersFamiliarCostReductionEffect extends CostModificationEffectImpl {

    private static final String effectText = "Activated abilities of creatures "
            + "you control cost {2} less to activate. "
            + "This effect can't reduce the mana in that cost to less than one mana";

    BiomancersFamiliarCostReductionEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = effectText;
    }

    private BiomancersFamiliarCostReductionEffect(final BiomancersFamiliarCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller == null) {
            return false;
        }
        int reduceMax = CardUtil.calculateActualPossibleGenericManaReduction(abilityToModify.getManaCostsToPay().getMana(), 2, 1);        
        if (reduceMax <= 0) {
            return true;
        }
        CardUtil.reduceCost(abilityToModify, reduceMax);
        return true;

    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getAbilityType() != AbilityType.ACTIVATED
                && (abilityToModify.getAbilityType() != AbilityType.MANA
                || !(abilityToModify instanceof ActivatedAbility))) {
            return false;
        }
        //Activated abilities of creatures you control
        Permanent permanent = game.getPermanentOrLKIBattlefield(abilityToModify.getSourceId());
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public BiomancersFamiliarCostReductionEffect copy() {
        return new BiomancersFamiliarCostReductionEffect(this);
    }
}

class BiomancersFamiliarReplacementEffect extends ReplacementEffectImpl {

    BiomancersFamiliarReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next time target creature adapts this turn, "
                + "it adapts as though it had no +1/+1 counters on it.";
    }

    private BiomancersFamiliarReplacementEffect(final BiomancersFamiliarReplacementEffect effect) {
        super(effect);
    }

    @Override
    public BiomancersFamiliarReplacementEffect copy() {
        return new BiomancersFamiliarReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADAPT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(targetPointer.getFirst(game, source));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setFlag(true);
        discard();
        return false;
    }
}
