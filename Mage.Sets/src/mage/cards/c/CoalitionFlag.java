
package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.SetCardSubtypeAttachedEffect;
import mage.abilities.effects.common.ruleModifying.TargetsHaveToTargetPermanentIfAbleEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
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
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature is a Flagbearer.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetCardSubtypeAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.AURA, SubType.FLAGBEARER)));

        // While choosing targets as part of casting a spell or activating an ability, your opponents must choose at least one Flagbearer on the battlefield if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TargetsHaveToTargetPermanentIfAbleEffect(new FilterPermanent(SubType.FLAGBEARER, "one Flagbearer"))));
    }

    public CoalitionFlag(final CoalitionFlag card) {
        super(card);
    }

    @Override
    public CoalitionFlag copy() {
        return new CoalitionFlag(this);
    }
}
