package mage.cards.p;

import mage.abilities.condition.common.BeheldDragonCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.BeholdDragonAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PiercingExhale extends CardImpl {

    public PiercingExhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // As an additional cost to cast this spell, you may behold a Dragon.
        this.addAbility(new BeholdDragonAbility());

        // Target creature you control deals damage equal to its power to target creature or planeswalker. If a Dragon was beheld, surveil 2.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SurveilEffect(2), BeheldDragonCondition.instance,
                "if a Dragon was beheld, surveil 2"
        ));
    }

    private PiercingExhale(final PiercingExhale card) {
        super(card);
    }

    @Override
    public PiercingExhale copy() {
        return new PiercingExhale(this);
    }
}
