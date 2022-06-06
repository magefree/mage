
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author jonubuu
 */
public final class LordOfTheAccursed extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ZOMBIE, "Zombies");

    public LordOfTheAccursed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Other Zombie creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
    
        //{2}{B}, Tap: All Zombies gain menace until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                new MenaceAbility(),
                Duration.EndOfTurn,
                filter, "All Zombies gain menace until end of turn."),
                new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private LordOfTheAccursed(final LordOfTheAccursed card) {
        super(card);
    }

    @Override
    public LordOfTheAccursed copy() {
        return new LordOfTheAccursed(this);
    }
}