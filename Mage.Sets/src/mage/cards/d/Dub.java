package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author JRHerlehy
 *         Created on 4/6/18.
 */
public final class Dub extends CardImpl {

    public Dub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2, has first strike, and is a Knight in addition to its other types.
        Effect effect = new BoostEnchantedEffect(2, 2);
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", has first strike");
        ability.addEffect(effect);
        effect = new AddCardSubtypeAttachedEffect(SubType.KNIGHT, Duration.WhileOnBattlefield, AttachmentType.AURA);
        effect.setText(", and is a Knight in addition to its other types");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private Dub(final Dub card) {
        super(card);
    }

    @Override
    public Dub copy() {
        return new Dub(this);
    }

}
