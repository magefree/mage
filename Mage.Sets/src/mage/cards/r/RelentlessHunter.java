
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class RelentlessHunter extends CardImpl {

    public RelentlessHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{R}{G}: Relentless Hunter gets +1/+1 and gains trample until end of turn.
        Effect effect1 = new BoostSourceEffect(1, 1, Duration.EndOfTurn);
        effect1.setText("{this} gets +1/+1");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect1, new ManaCostsImpl<>("{1}{R}{G}"));
        Effect effect2 = new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect2.setText("and gains trample until end of turn");
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    private RelentlessHunter(final RelentlessHunter card) {
        super(card);
    }

    @Override
    public RelentlessHunter copy() {
        return new RelentlessHunter(this);
    }
}
