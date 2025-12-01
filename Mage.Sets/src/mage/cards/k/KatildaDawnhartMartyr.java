package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.*;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KatildaDawnhartMartyr extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.VAMPIRE, "Vampires");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent("permanents you control that are Spirits and/or enchantments");

    static {
        filter2.add(Predicates.or(
                SubType.SPIRIT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2);
    private static final Hint hint = new ValueHint("Spirits and enchantments you control", xValue);

    public KatildaDawnhartMartyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.WARLOCK}, "{1}{W}{W}",
                "Katilda's Rising Dawn",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "W"
        );

        // Katilda, Dawnhart Martyr
        this.getLeftHalfCard().setPT(0, 0);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // Protection from Vampires
        this.getLeftHalfCard().addAbility(new ProtectionAbility(filter));

        // Katilda, Dawnhart Martyr's power and toughness are each equal to the number of permanents you control that are Spirits and/or enchantments.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue)
        ).addHint(hint));

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Disturb {3}{W}{W}
        // needs to be added after right half has spell ability target set
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{3}{W}{W}"));

        // Katilda's Rising Dawn
        // Enchanted creature has flying, lifelink, and protection from Vampires, and it gets +X/+X where X is the number of permanents you control that are Spirits and/or enchantments.
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.AURA
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                LifelinkAbility.getInstance(), AttachmentType.AURA
        ).setText(", lifelink"));
        ability.addEffect(new GainAbilityAttachedEffect(
                new ProtectionAbility(filter), AttachmentType.AURA
        ).setText(", and protection from Vampires"));
        ability.addEffect(new BoostEquippedEffect(xValue, xValue)
                .setText(", and it gets +X/+X, where X is the number of permanents you control that are Spirits and/or enchantments"));
        this.getRightHalfCard().addAbility(ability.addHint(hint));

        // If Katilda's Rising Dawn would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private KatildaDawnhartMartyr(final KatildaDawnhartMartyr card) {
        super(card);
    }

    @Override
    public KatildaDawnhartMartyr copy() {
        return new KatildaDawnhartMartyr(this);
    }
}
