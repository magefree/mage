package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.CommanderPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public final class MythUnbound extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new CommanderPredicate());
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public MythUnbound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Your commander costs {1} less to cast for each time it's been cast from the command zone this game.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new MythUnboundCostReductionEffect()
        ));

        // Whenever your commander is put into the command zone from anywhere, draw a card.
        this.addAbility(new ZoneChangeAllTriggeredAbility(
                Zone.BATTLEFIELD, Zone.ALL, Zone.COMMAND,
                new DrawCardSourceControllerEffect(1), filter,
                "Whenever your commander is put into "
                + "the command zone from anywhere, ", false
        ));
    }

    public MythUnbound(final MythUnbound card) {
        super(card);
    }

    @Override
    public MythUnbound copy() {
        return new MythUnbound(this);
    }
}

class MythUnboundCostReductionEffect extends CostModificationEffectImpl {

    MythUnboundCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "your commander costs {1} less to cast for each time "
                + "it's been cast from the command zone this game";
    }

    MythUnboundCostReductionEffect(MythUnboundCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Ability spellAbility = (SpellAbility) abilityToModify;
        if (spellAbility != null) {
            Integer amount = (Integer) game.getState().getValue(abilityToModify.getControllerId() + "_castCount");
            if (amount != null && amount > 0) {
                CardUtil.reduceCost(spellAbility, amount);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (abilityToModify instanceof SpellAbility) {
            if (abilityToModify.isControlledBy(source.getControllerId())) {
                Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
                if (spell != null) {
                    return player.getCommandersIds().contains(spell.getId());
                }
            }
        }
        return false;
    }

    @Override
    public MythUnboundCostReductionEffect copy() {
        return new MythUnboundCostReductionEffect(this);
    }
}
