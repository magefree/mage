
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class PowerArmor extends CardImpl {

    public PowerArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Domain - {3}, {tap}: Target creature gets +1/+1 until end of turn for each basic land type among lands you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(
                DomainValue.REGULAR, DomainValue.REGULAR, Duration.EndOfTurn), new TapSourceCost());
        ability.addCost(new ManaCostsImpl<>("{3}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.setAbilityWord(AbilityWord.DOMAIN);
        this.addAbility(ability.addHint(DomainHint.instance));
    }

    private PowerArmor(final PowerArmor card) {
        super(card);
    }

    @Override
    public PowerArmor copy() {
        return new PowerArmor(this);
    }
}
