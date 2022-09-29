package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FallFromFavor extends CardImpl {

    public FallFromFavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Fall from Favor enters the battlefield, tap enchanted creature and you become the monarch.
        ability = new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect());
        ability.addEffect(new BecomesMonarchSourceEffect().concatBy("and"));
        this.addAbility(ability);

        // Enchanted creature doesn't untap during its controller's untap step unless that player is the monarch.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousRuleModifyingEffect(
                new DontUntapInControllersUntapStepEnchantedEffect(), MonarchIsSourceControllerCondition.instance
        ).setText("enchanted creature doesn't untap during its controller's untap step unless that player is the monarch")));
    }

    private FallFromFavor(final FallFromFavor card) {
        super(card);
    }

    @Override
    public FallFromFavor copy() {
        return new FallFromFavor(this);
    }
}
