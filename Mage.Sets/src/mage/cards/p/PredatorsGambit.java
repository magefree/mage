
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
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

    private static final String rule = "Enchanted creature has intimidate as long as its controller controls no other creatures";

    public PredatorsGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));

        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 1, Duration.WhileOnBattlefield)));

        // Enchanted creature has intimidate as long as its controller controls no other creatures.
        ContinuousEffect effect = new GainAbilityAttachedEffect(IntimidateAbility.getInstance(), AttachmentType.AURA);
        ConditionalContinuousEffect intimidate = new ConditionalContinuousEffect(effect, new CreatureCountCondition(1, TargetController.YOU), rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, intimidate));

    }

    private PredatorsGambit(final PredatorsGambit card) {
        super(card);
    }

    @Override
    public PredatorsGambit copy() {
        return new PredatorsGambit(this);
    }
}
