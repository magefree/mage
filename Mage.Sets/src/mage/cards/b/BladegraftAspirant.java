package mage.cards.b;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author @stwalsh4118
 */
public final class BladegraftAspirant extends CardImpl {

    private static final FilterCard filter = new FilterCard("Equipment spells");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }


    public BladegraftAspirant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Equipment spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Activated abilities of Equipment you control that target Bladegraft Aspirant cost {1} less to activate.
        
        this.addAbility(new SimpleStaticAbility(new BladegraftAspirantCostReductionEffect()));

    }

    private BladegraftAspirant(final BladegraftAspirant card) {
        super(card);
    }

    @Override
    public BladegraftAspirant copy() {
        return new BladegraftAspirant(this);
    }
}

class BladegraftAspirantCostReductionEffect extends CostModificationEffectImpl {

    private static final String effectText = "Activated abilities of Equipment you control that target Bladegraft Aspirant cost {1} less to activate.";

            BladegraftAspirantCostReductionEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = effectText;
    }

    private BladegraftAspirantCostReductionEffect(final BladegraftAspirantCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller == null) {
            return false;
        }
        int reduceMax = CardUtil.calculateActualPossibleGenericManaReduction(abilityToModify.getManaCostsToPay().getMana(), 1, 0);        
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

        Permanent permanent = game.getPermanentOrLKIBattlefield(abilityToModify.getSourceId());

        if(!(permanent != null && permanent.getSubtype(game).contains(SubType.EQUIPMENT) && permanent.isControlledBy(source.getControllerId()))) {
            return false;
        }


        if (game.getStack().getStackObject(abilityToModify.getId()) != null) {
            Set<UUID> allTargets = CardUtil.getAllSelectedTargets(abilityToModify, game);
            return allTargets.contains(source.getSourceId());
        } else {
            Set<UUID> allTargets = CardUtil.getAllPossibleTargets(abilityToModify, game);
            return allTargets.contains(source.getSourceId());
        }
    }

    @Override
    public BladegraftAspirantCostReductionEffect copy() {
        return new BladegraftAspirantCostReductionEffect(this);
    }
}
