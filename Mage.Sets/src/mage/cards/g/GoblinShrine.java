
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
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
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class GoblinShrine extends CardImpl {

    private static final FilterCreaturePermanent filterGoblin = new FilterCreaturePermanent("Goblin creature");
    private static final String rule = "As long as enchanted land is a basic Mountain, Goblin creatures get +1/+0.";

    static {
        filterGoblin.add(SubType.GOBLIN.getPredicate());
    }

    public GoblinShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // As long as enchanted land is a basic Mountain, Goblin creatures get +1/+0.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostAllEffect(1, 0, Duration.WhileOnBattlefield, filterGoblin, false), new EnchantedPermanentSubtypeCondition(SubType.MOUNTAIN), rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

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

class EnchantedPermanentSubtypeCondition implements Condition {

    private final FilterLandPermanent filter = new FilterLandPermanent();

    public EnchantedPermanentSubtypeCondition(SubType subType) {
        filter.add(subType.getPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                return filter.match(permanent, enchantment.getControllerId(), source, game);
                }
            }
        return false;
    }

    @Override
    public String toString() {
        return filter.getMessage();
    }

}
