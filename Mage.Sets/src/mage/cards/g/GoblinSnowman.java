
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.abilities.effects.common.PreventCombatDamageToSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BursegSardaukar
 */
public final class GoblinSnowman extends CardImpl {

    public GoblinSnowman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Whenever Goblin Snowman blocks, prevent all combat damage that would be dealt to and dealt by it this turn.
        Effect effect = new PreventCombatDamageBySourceEffect(Duration.EndOfTurn);
        effect.setText("prevent all combat damage that would be dealt to");
        Ability ability = new BlocksSourceTriggeredAbility(effect, false);
        effect = new PreventCombatDamageToSourceEffect(Duration.EndOfTurn);
        effect.setText("and dealt by it this turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        //{T}: Goblin Snowman deals 1 damage to target creature it's blocking.
        FilterAttackingCreature filter = new FilterAttackingCreature("creature it's blocking");
        filter.add(new BlockedByIdPredicate(this.getId()));
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private GoblinSnowman(final GoblinSnowman card) {
        super(card);
    }

    @Override
    public GoblinSnowman copy() {
        return new GoblinSnowman(this);
    }
}
