
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author nigelzor
 */
public final class WildAesthir extends CardImpl {

    public WildAesthir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // {W}{W}: Wild Aesthir gets +2/+0 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{W}{W}")));
    }

    private WildAesthir(final WildAesthir card) {
        super(card);
    }

    @Override
    public WildAesthir copy() {
        return new WildAesthir(this);
    }
}
