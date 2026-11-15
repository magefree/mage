
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class GoblinShrine extends CardImpl {

    private static final FilterCreaturePermanent filterGoblin = new FilterCreaturePermanent("Goblin creature");
    private static final FilterPermanent filterMountain = new FilterLandPermanent("enchanted land is a basic Mountain");
    private static final String rule = "As long as enchanted land is a basic Mountain, Goblin creatures get +1/+0.";

    static {
        filterGoblin.add(SubType.GOBLIN.getPredicate());
        filterMountain.add(SuperType.BASIC.getPredicate());
        filterMountain.add(SubType.MOUNTAIN.getPredicate());
    }

    public GoblinShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // As long as enchanted land is a basic Mountain, Goblin creatures get +1/+0.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostAllEffect(1, 0, Duration.WhileOnBattlefield, filterGoblin, false), new AttachedToMatchesFilterCondition(filterMountain), rule);
        this.addAbility(new SimpleStaticAbility(effect));

        // When Goblin Shrine leaves the battlefield, it deals 1 damage to each Goblin creature.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DamageAllEffect(1, "it", filterGoblin), false));

    }

    private GoblinShrine(final GoblinShrine card) {
        super(card);
    }

    @Override
    public GoblinShrine copy() {
        return new GoblinShrine(this);
    }
}
