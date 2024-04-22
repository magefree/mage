package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
public final class KatildasRisingDawn extends CardImpl {

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

    public KatildasRisingDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AURA);
        this.color.setWhite(true);
        this.nightCard = true;

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

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
        this.addAbility(ability.addHint(hint));

        // If Katilda's Rising Dawn would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new ExileSourceEffect().setText("exile it instead")));
    }

    private KatildasRisingDawn(final KatildasRisingDawn card) {
        super(card);
    }

    @Override
    public KatildasRisingDawn copy() {
        return new KatildasRisingDawn(this);
    }
}
