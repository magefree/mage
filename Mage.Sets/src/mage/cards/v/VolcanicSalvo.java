package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VolcanicSalvo extends CardImpl {

    public VolcanicSalvo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{10}{R}{R}");

        // This spell costs {X} less to cast, where X is the total power of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new VolcanicSalvoCostReductionEffect()).setRuleAtTheTop(true));

        // Volcanic Salvo deals 6 damage to each of up to two target creatures and/or planeswalkers.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6)
                .setText("{this} deals 6 damage to each of up to two target creatures and/or planeswalkers")
        );
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker(0, 2));
    }

    private VolcanicSalvo(final VolcanicSalvo card) {
        super(card);
    }

    @Override
    public VolcanicSalvo copy() {
        return new VolcanicSalvo(this);
    }
}

class VolcanicSalvoCostReductionEffect extends CostModificationEffectImpl {

    VolcanicSalvoCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {X} less to cast, where X is the total power of creatures you control";
    }

    private VolcanicSalvoCostReductionEffect(final VolcanicSalvoCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int reductionAmount = game.getBattlefield()
                .getAllActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game
                ).stream()
                .map(Permanent::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
        CardUtil.reduceCost(abilityToModify, Math.max(0, reductionAmount));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId())
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public VolcanicSalvoCostReductionEffect copy() {
        return new VolcanicSalvoCostReductionEffect(this);
    }
}
