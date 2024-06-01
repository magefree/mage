package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class CageOfHands extends CardImpl {

    public CageOfHands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't attack or block.
        this.addAbility(new SimpleStaticAbility(new CantAttackBlockAttachedEffect(AttachmentType.AURA)));

        // {1}{W}: Return Cage of Hands to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{1}{W}")));
    }

    private CageOfHands(final CageOfHands card) {
        super(card);
    }

    @Override
    public CageOfHands copy() {
        return new CageOfHands(this);
    }
}