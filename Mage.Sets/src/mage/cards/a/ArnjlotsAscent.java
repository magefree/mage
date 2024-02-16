
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class ArnjlotsAscent extends CardImpl {

    public ArnjlotsAscent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{U}");

        // Cumulative upkeep {U}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{U}")));
        // {1}: Target creature gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(FlyingAbility.getInstance(),
            Duration.EndOfTurn), new ManaCostsImpl<>("{1}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ArnjlotsAscent(final ArnjlotsAscent card) {
        super(card);
    }

    @Override
    public ArnjlotsAscent copy() {
        return new ArnjlotsAscent(this);
    }
}
