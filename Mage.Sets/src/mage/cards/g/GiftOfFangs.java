package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
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
 * @author weirddan455
 */
public final class GiftOfFangs extends CardImpl {

    private static final Condition condition1 = new EquippedHasSubtypeCondition(SubType.VAMPIRE);
    private static final Condition condition2 = new InvertCondition(condition1);

    public GiftOfFangs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Neutral));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 as long as it's a Vampire. Otherwise, it gets -2/-2.
        ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(2, 2), condition1,
                "Enchanted creature gets +2/+2 as long as it's a Vampire"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(-2, -2), condition2,
                "Otherwise, it gets -2/-2"
        ));
        this.addAbility(ability);
    }

    private GiftOfFangs(final GiftOfFangs card) {
        super(card);
    }

    @Override
    public GiftOfFangs copy() {
        return new GiftOfFangs(this);
    }
}
