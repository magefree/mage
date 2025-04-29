package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.BeholdDragonCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoltenExhale extends CardImpl {

    public MoltenExhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // You may cast this spell as though it had flash if you behold a Dragon as an additional cost to cast it.
        Ability ability = new PayMoreToCastAsThoughtItHadFlashAbility(this, new BeholdDragonCost(),
                "you may cast this spell as though it had flash if you behold a Dragon as an additional cost to cast it.");
        Target target = new TargetCreatureOrPlaneswalker();
        Effect effect = new DamageTargetEffect(4);
        ability.addEffect(effect);
        ability.addTarget(target);
        ability.setAdditionalCostsRuleVisible(false);
        this.addAbility(ability);

        // Molten Exhale deals 4 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(target);
    }

    private MoltenExhale(final MoltenExhale card) {
        super(card);
    }

    @Override
    public MoltenExhale copy() {
        return new MoltenExhale(this);
    }
}
