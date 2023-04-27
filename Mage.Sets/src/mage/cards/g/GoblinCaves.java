
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author nigelzor
 */
public final class GoblinCaves extends CardImpl {

    public GoblinCaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        this.addAbility(new EnchantAbility(auraTarget));
        // As long as enchanted land is a basic Mountain, Goblin creatures get +0/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostAllEffect(0, 2, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS, false),
                new AttachedToBasicMountainCondition(),
                "As long as enchanted land is a basic Mountain, Goblin creatures get +0/+2"
        )));
    }

    private GoblinCaves(final GoblinCaves card) {
        super(card);
    }

    @Override
    public GoblinCaves copy() {
        return new GoblinCaves(this);
    }
}

class AttachedToBasicMountainCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (enchanted != null) {
                if (enchanted.hasSubtype(SubType.MOUNTAIN, game) && enchanted.isBasic()) {
                    return true;
                }
            }
        }
        return false;
    }
}
