package mage.cards.a;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author FenrisulfrX
 */
public final class AgonizingDemise extends CardImpl {

    public AgonizingDemise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Kicker {1}{R}
        this.addAbility(new KickerAbility("{1}{R}"));

        // Destroy target nonblack creature. It can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));

        // If Agonizing Demise was kicked, it deals damage equal to that creature's power to the creature's controller.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetControllerEffect(TargetPermanentPowerCount.instance),
                KickedCondition.instance,
                "if this spell was kicked, it deals damage equal to that creature's power to the creature's controller."));

    }

    private AgonizingDemise(final AgonizingDemise card) {
        super(card);
    }

    @Override
    public AgonizingDemise copy() {
        return new AgonizingDemise(this);
    }
}
