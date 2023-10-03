
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ClashOfRealities extends CardImpl {

    private static final FilterCreaturePermanent filterSpirit = new FilterCreaturePermanent("Spirit creature");
    private static final FilterCreaturePermanent filterNotSpirit = new FilterCreaturePermanent("non-Spirit creature");

    static {
        filterSpirit.add(SubType.SPIRIT.getPredicate());
        filterNotSpirit.add(Predicates.not(SubType.SPIRIT.getPredicate()));
    }

    public ClashOfRealities(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");


        // All Spirits have "When this permanent enters the battlefield, you may have it deal 3 damage to target non-Spirit creature."
        Ability ability1 = new ClashOfRealitiesTriggeredAbility(new DamageTargetEffect(3), "When this permanent enters the battlefield, ");
        ability1.addTarget(new TargetCreaturePermanent(filterNotSpirit));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ability1, Duration.WhileOnBattlefield, filterSpirit, "All Spirits have \"When this permanent enters the battlefield, you may have it deal 3 damage to target non-Spirit creature.\"")));

        // Non-Spirit creatures have "When this creature enters the battlefield, you may have it deal 3 damage to target Spirit creature."
        Ability ability2 = new ClashOfRealitiesTriggeredAbility(new DamageTargetEffect(3), "When this creature enters the battlefield, ");
        ability2.addTarget(new TargetCreaturePermanent(filterSpirit));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ability2, Duration.WhileOnBattlefield, filterNotSpirit, "Non-Spirit creatures have \"When this creature enters the battlefield, you may have it deal 3 damage to target Spirit creature.\"")));
    }

    private ClashOfRealities(final ClashOfRealities card) {
        super(card);
    }

    @Override
    public ClashOfRealities copy() {
        return new ClashOfRealities(this);
    }

    private static class ClashOfRealitiesTriggeredAbility extends ZoneChangeTriggeredAbility {

        public ClashOfRealitiesTriggeredAbility(Effect effect, String rule) {
            super(Zone.BATTLEFIELD, effect, rule, true);
        }

        private ClashOfRealitiesTriggeredAbility(final ClashOfRealitiesTriggeredAbility ability) {
            super(ability);
        }


        @Override
        public ClashOfRealitiesTriggeredAbility copy() {
            return new ClashOfRealitiesTriggeredAbility(this);
        }
    }
}
