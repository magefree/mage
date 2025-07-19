
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
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
 * @author TheElk801
 */
public final class BurningPalmEfreet extends CardImpl {

    public BurningPalmEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.EFREET);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{R}{R}: Burning Palm Efreet deals 2 damage to target creature with flying and that creature loses flying until end of turn.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new ManaCostsImpl<>("{1}{R}{R}"));
        ability.addEffect(new LoseAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn)
                .setText("and that creature loses flying until end of turn")
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);
    }

    private BurningPalmEfreet(final BurningPalmEfreet card) {
        super(card);
    }

    @Override
    public BurningPalmEfreet copy() {
        return new BurningPalmEfreet(this);
    }
}
