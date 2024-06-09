package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttachedPermanentToughnessCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseLifeControllerAttachedEffect;
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
 *
 * @author Plopman
 */
public final class BanewaspAffliction extends CardImpl {

    public BanewaspAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted creature dies, that creature's controller loses life equal to its toughness.
        this.addAbility( new DiesAttachedTriggeredAbility(
                new LoseLifeControllerAttachedEffect(AttachedPermanentToughnessCount.instance)
                        .setText("that creature's controller loses life equal to its toughness"),
                "enchanted creature"));
    }

    private BanewaspAffliction(final BanewaspAffliction card) {
        super(card);
    }

    @Override
    public BanewaspAffliction copy() {
        return new BanewaspAffliction(this);
    }
}
