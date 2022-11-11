package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TallAsABeanstalk extends CardImpl {

    public TallAsABeanstalk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +3/+3, has reach, and is a Giant in addition to its other types.
        ability = new SimpleStaticAbility(
                new BoostEnchantedEffect(3, 3).setText("enchanted creature gets +3/+3")
        );
        ability.addEffect(new GainAbilityAttachedEffect(
                ReachAbility.getInstance(), AttachmentType.AURA
        ).setText(", has reach"));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.GIANT, Duration.WhileOnBattlefield, AttachmentType.AURA
        ).setText(", and is a Giant in addition to its other types"));
        this.addAbility(ability);
    }

    private TallAsABeanstalk(final TallAsABeanstalk card) {
        super(card);
    }

    @Override
    public TallAsABeanstalk copy() {
        return new TallAsABeanstalk(this);
    }
}
