
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class NobleSteeds extends CardImpl {

    public NobleSteeds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // {1}{W}: Target creature gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{W}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NobleSteeds(final NobleSteeds card) {
        super(card);
    }

    @Override
    public NobleSteeds copy() {
        return new NobleSteeds(this);
    }
}
