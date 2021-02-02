
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author TheElk801
 */
public final class SawbackManticore extends CardImpl {

    public SawbackManticore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.subtype.add(SubType.MANTICORE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {4}: Sawback Manticore gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new GenericManaCost(4)));

        // {1}: Sawback Manticore deals 2 damage to target attacking or blocking creature. Activate this ability only if Sawback Manticore is attacking or blocking and only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(2), new GenericManaCost(1),
                1, new SourceMatchesFilterCondition("if {this} is attacking or blocking", new FilterAttackingOrBlockingCreature())
        );
        ability.addTarget(new TargetAttackingOrBlockingCreature());
        this.addAbility(ability);
    }

    private SawbackManticore(final SawbackManticore card) {
        super(card);
    }

    @Override
    public SawbackManticore copy() {
        return new SawbackManticore(this);
    }
}
