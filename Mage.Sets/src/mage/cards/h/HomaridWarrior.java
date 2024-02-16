
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class HomaridWarrior extends CardImpl {

    public HomaridWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.HOMARID);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {U}: Homarid Warrior gains shroud until end of turn and doesn't untap during your next untap step. Tap Homarid Warrior.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(
            ShroudAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{U}"));
        Effect effect = new DontUntapInControllersNextUntapStepSourceEffect();
        effect.setText("and doesn't untap during your next untap step");
        ability.addEffect(effect);
        ability.addEffect(new TapSourceEffect());
        this.addAbility(ability);
    }

    private HomaridWarrior(final HomaridWarrior card) {
        super(card);
    }

    @Override
    public HomaridWarrior copy() {
        return new HomaridWarrior(this);
    }
}
