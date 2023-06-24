package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class StrengthOfUnity extends CardImpl {

    public StrengthOfUnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Domain - Enchanted creature gets +1/+1 for each basic land type among lands you control.
        Effect effect = new BoostEnchantedEffect(DomainValue.REGULAR, DomainValue.REGULAR);
        effect.setText("<i>Domain</i> &mdash; Enchanted creature gets +1/+1 for each basic land type among lands you control.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect).addHint(DomainHint.instance));
    }

    private StrengthOfUnity(final StrengthOfUnity card) {
        super(card);
    }

    @Override
    public StrengthOfUnity copy() {
        return new StrengthOfUnity(this);
    }
}
