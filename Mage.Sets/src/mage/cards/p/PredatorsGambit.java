package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author noxx
 */
public final class PredatorsGambit extends CardImpl {

    private static final String rule = "Enchanted creature has intimidate as long as its controller controls no other creatures. " +
            "<i>(It can't be blocked except by artifact creatures and/or creatures that share a color with it.)</i>";

    public PredatorsGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));

        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(2, 1)));

        // Enchanted creature has intimidate as long as its controller controls no other creatures.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(IntimidateAbility.getInstance(), AttachmentType.AURA),
                new CreatureCountCondition(1, TargetController.YOU),
                rule
        )));

    }

    private PredatorsGambit(final PredatorsGambit card) {
        super(card);
    }

    @Override
    public PredatorsGambit copy() {
        return new PredatorsGambit(this);
    }
}
