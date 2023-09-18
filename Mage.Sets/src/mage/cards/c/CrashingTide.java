
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CrashingTide extends CardImpl {

    public CrashingTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Crashing tide has flash as long as you control a Merfolk.
        FilterControlledPermanent filter = new FilterControlledPermanent();
        filter.add(SubType.MERFOLK.getPredicate());
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlashAbility.getInstance(), Duration.WhileOnBattlefield, true),
                        new PermanentsOnTheBattlefieldCondition(filter),
                        "this spell has flash as long as you control a Merfolk")).setRuleAtTheTop(true));

        // Return target creature to it's owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private CrashingTide(final CrashingTide card) {
        super(card);
    }

    @Override
    public CrashingTide copy() {
        return new CrashingTide(this);
    }
}
