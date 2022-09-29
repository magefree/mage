
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.SetCardSubtypeAttachedEffect;
import mage.abilities.effects.common.ruleModifying.TargetsHaveToTargetPermanentIfAbleEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CoalitionFlag extends CardImpl {

    public CoalitionFlag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature is a Flagbearer.
        this.addAbility(new SimpleStaticAbility(new SetCardSubtypeAttachedEffect(
                Duration.WhileOnBattlefield, AttachmentType.AURA, SubType.FLAGBEARER
        )));

        // While choosing targets as part of casting a spell or activating an ability, your opponents must choose at least one Flagbearer on the battlefield if able.
        this.addAbility(new SimpleStaticAbility(new TargetsHaveToTargetPermanentIfAbleEffect()));
    }

    private CoalitionFlag(final CoalitionFlag card) {
        super(card);
    }

    @Override
    public CoalitionFlag copy() {
        return new CoalitionFlag(this);
    }
}
