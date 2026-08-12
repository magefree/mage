package mage.cards.p;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class PunishingPunch extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    private static final Condition condition = new CardsInControllerGraveyardCondition(
        2, StaticFilters.FILTER_CARD_CREATURES
    );

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public PunishingPunch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // This spell costs {2} less to cast if there are two or more creature cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionSourceEffect(2, condition)
        ).setRuleAtTheTop(true));

        // Target creature you control deals damage equal to twice its power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("", 2));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private PunishingPunch(final PunishingPunch card) {
        super(card);
    }

    @Override
    public PunishingPunch copy() {
        return new PunishingPunch(this);
    }
}
