package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class GiantsGrasp extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.GIANT);

    public GiantsGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant Giant you control
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getSpellAbility().addTarget(auraTarget);

        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Giant's Grasp enters the battlefield, gain control of target nonland permanent for as long as Giant's Grasp remains on the battlefield.
        GainControlTargetEffect controlEffect = new GainControlTargetEffect(Duration.UntilSourceLeavesBattlefield);
        controlEffect.setText("gain control of target nonland permanent for as long as {this} remains on the battlefield");
        ability = new EntersBattlefieldTriggeredAbility(controlEffect);
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private GiantsGrasp(final GiantsGrasp card) {
        super(card);
    }

    @Override
    public GiantsGrasp copy() {
        return new GiantsGrasp(this);
    }
}
