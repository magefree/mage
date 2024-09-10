
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class FreewindEquenaut extends CardImpl {

    public FreewindEquenaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(2),
                new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(new FilterAttackingOrBlockingCreature()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(ability, Duration.WhileOnBattlefield),
                new EnchantedSourceCondition(),
                "As long as {this} is enchanted, it has \"{T}: {this} deals 2 damage to target attacking or blocking creature.\"")));
    }

    private FreewindEquenaut(final FreewindEquenaut card) {
        super(card);
    }

    @Override
    public FreewindEquenaut copy() {
        return new FreewindEquenaut(this);
    }
}
