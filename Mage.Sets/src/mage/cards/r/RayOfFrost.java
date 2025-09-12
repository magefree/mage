package mage.cards.r;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class RayOfFrost extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("enchanted creature is red");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    private static final Condition condition = new AttachedToMatchesFilterCondition(filter);

    public RayOfFrost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Removal));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Ray of Frost enters the battlefield, if enchanted creature is red, tap it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new TapEnchantedEffect().setText("tap it")
        ).withInterveningIf(condition));

        // As long as enchanted creature is red, it loses all abilities.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new LoseAllAbilitiesAttachedEffect(AttachmentType.AURA), condition,
                "as long as enchanted creature is red, it loses all abilities"
        )));

        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepEnchantedEffect()));
    }

    private RayOfFrost(final RayOfFrost card) {
        super(card);
    }

    @Override
    public RayOfFrost copy() {
        return new RayOfFrost(this);
    }
}
