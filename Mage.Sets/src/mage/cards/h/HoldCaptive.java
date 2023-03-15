package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockAttackActivateAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class HoldCaptive extends CardImpl {
    public HoldCaptive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{B}");
        this.addSubType(SubType.AURA);

        //Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        //Enchanted creature can't attack or block, and its activated abilities can't be activited.
        this.addAbility(new SimpleStaticAbility(new CantBlockAttackActivateAttachedEffect()));
    }

    public HoldCaptive(final HoldCaptive card) {
        super(card);
    }

    @Override
    public HoldCaptive copy() {
        return new HoldCaptive(this);
    }
}
