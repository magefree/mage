package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Galatolol
 */
public final class Cagemail extends CardImpl {

    public Cagemail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature gets +2/+2 and can't attack.
        Ability ability1 = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2, Duration.WhileOnBattlefield));
        Effect effect = new CantAttackAttachedEffect(AttachmentType.AURA);
        effect.setText("and can't attack.");
        ability1.addEffect(effect);
        this.addAbility(ability1);
    }

    private Cagemail(final Cagemail card) {
        super(card);
    }

    @Override
    public Cagemail copy() {
        return new Cagemail(this);
    }
}
