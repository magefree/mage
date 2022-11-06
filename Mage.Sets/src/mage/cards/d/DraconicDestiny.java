package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class DraconicDestiny extends CardImpl {

    public DraconicDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has flying, haste, and "{1}: This creature gets +1/+0 until end of turn." It's a Dragon in addition to its other types.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA).setText("and has flying"));
        ability.addEffect(new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.AURA).setText(", haste"));
        ability.addEffect(new GainAbilityAttachedEffect(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), new GenericManaCost(1)
        ), AttachmentType.AURA).setText(", and \"{1}: This creature gets +1/+0 until end of turn.\""));
        ability.addEffect(new AddCardSubtypeAttachedEffect(SubType.DRAGON, Duration.WhileOnBattlefield, AttachmentType.AURA)
                .setText("It's a Dragon in addition to its other types"));
        this.addAbility(ability);

        // When enchanted creature dies, return Draconic Destiny to its owner's hand.
        this.addAbility(new DiesAttachedTriggeredAbility(new ReturnToHandSourceEffect(false, true), "enchanted creature"));
    }

    private DraconicDestiny(final DraconicDestiny card) {
        super(card);
    }

    @Override
    public DraconicDestiny copy() {
        return new DraconicDestiny(this);
    }
}
