package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class BondsOfFaith extends CardImpl {

    private static final Condition condition1 = new EquippedHasSubtypeCondition(SubType.HUMAN);
    private static final Condition condition2 = new InvertCondition(condition1);

    public BondsOfFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Neutral));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +2/+2 as long as it's a Human. Otherwise, it can't attack or block.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEquippedEffect(2, 2), condition1,
                "Enchanted creature gets +2/+2 as long as it's a Human"
        ));
        ability.addEffect(new ConditionalRestrictionEffect(
                new CantAttackBlockAttachedEffect(AttachmentType.AURA),
                condition2, "Otherwise, it can't attack or block"
        ));
        this.addAbility(ability);
    }

    private BondsOfFaith(final BondsOfFaith card) {
        super(card);
    }

    @Override
    public BondsOfFaith copy() {
        return new BondsOfFaith(this);
    }
}
