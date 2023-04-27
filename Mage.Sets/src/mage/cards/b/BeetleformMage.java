

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */


public final class BeetleformMage extends CardImpl {

    public BeetleformMage (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{U}");
        this.subtype.add(SubType.HUMAN, SubType.INSECT, SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}{U}: Beetleform Mage gets +2/+2 and gains flying until end of turn. Activate this ability only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2,2, Duration.EndOfTurn).setText("{this} gets +2/+2"), new ManaCostsImpl<>("{G}{U}"));
        ability.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn).setText("and gains flying until end of turn"));
        this.addAbility(ability);

    }

    public BeetleformMage (final BeetleformMage card) {
        super(card);
    }

    @Override
    public BeetleformMage copy() {
        return new BeetleformMage(this);
    }

}
