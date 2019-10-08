package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Embercleave extends CardImpl {

    public Embercleave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // This spell costs {1} less to cast for each attacking creature you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new EmbercleaveCostReductionEffect()));

        // When Embercleave enters the battlefield, attach it to target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AttachEffect(
                Outcome.BoostCreature, "attach it to target creature you control"
        ), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Equipped creature gets +1/+1 and has double strike and trample.
        ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has double strike"));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and trample"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private Embercleave(final Embercleave card) {
        super(card);
    }

    @Override
    public Embercleave copy() {
        return new Embercleave(this);
    }
}

class EmbercleaveCostReductionEffect extends CostModificationEffectImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AttackingPredicate.instance);
    }

    EmbercleaveCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {1} less to cast for each attacking creature you control";
    }

    private EmbercleaveCostReductionEffect(EmbercleaveCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int reductionAmount = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
        CardUtil.reduceCost(abilityToModify, reductionAmount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility) && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public EmbercleaveCostReductionEffect copy() {
        return new EmbercleaveCostReductionEffect(this);
    }
}
