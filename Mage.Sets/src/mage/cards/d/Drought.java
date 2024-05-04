package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Drought extends CardImpl {

    public Drought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // At the beginning of your upkeep, sacrifice Drought unless you pay {W}{W}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{W}{W}")), TargetController.YOU, false));

        // Spells cost an additional "Sacrifice a Swamp" to cast for each black mana symbol in their mana costs.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DroughtAdditionalCostEffect(true)));

        // Activated abilities cost an additional "Sacrifice a Swamp" to activate for each black mana symbol in their activation costs.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DroughtAdditionalCostEffect(false)));
    }

    private Drought(final Drought card) {
        super(card);
    }

    @Override
    public Drought copy() {
        return new Drought(this);
    }
}

class DroughtAdditionalCostEffect extends CostModificationEffectImpl {

    private final boolean appliesToSpells;
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Swamp");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    DroughtAdditionalCostEffect(boolean appliesToSpells) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.staticText = (appliesToSpells ? "Spells" : "Activated abilities") + " cost an additional \"Sacrifice a Swamp\" to activate for each black mana symbol in their " + (appliesToSpells ? "mana" : "activation") + " costs";
        this.appliesToSpells = appliesToSpells;
    }

    private DroughtAdditionalCostEffect(final DroughtAdditionalCostEffect effect) {
        super(effect);
        appliesToSpells = effect.appliesToSpells;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int blackSymbols = abilityToModify.getManaCosts().getMana().getBlack();
        abilityToModify.addCost(new SacrificeTargetCost(blackSymbols, filter));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return (appliesToSpells && abilityToModify.getAbilityType() == AbilityType.SPELL)
                || (!appliesToSpells && abilityToModify.isActivatedAbility());
    }

    @Override
    public DroughtAdditionalCostEffect copy() {
        return new DroughtAdditionalCostEffect(this);
    }
}
