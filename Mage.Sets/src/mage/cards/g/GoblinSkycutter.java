
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class GoblinSkycutter extends CardImpl {

    public GoblinSkycutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Sacrifice Goblin Skycutter: Goblin Skycutter deals 2 damage to target creature with flying. That creature loses flying until end of turn.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2, "it"), new SacrificeSourceCost());
        ability.addEffect(new LoseAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn).setText("that creature loses flying until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);
    }

    private GoblinSkycutter(final GoblinSkycutter card) {
        super(card);
    }

    @Override
    public GoblinSkycutter copy() {
        return new GoblinSkycutter(this);
    }
}
