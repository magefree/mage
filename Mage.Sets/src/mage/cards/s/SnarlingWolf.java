package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SnarlingWolf extends CardImpl {

    public SnarlingWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{G}: Snarling Wolf gets +2/+2 until end of turn. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{G}")
        ));
    }

    private SnarlingWolf(final SnarlingWolf card) {
        super(card);
    }

    @Override
    public SnarlingWolf copy() {
        return new SnarlingWolf(this);
    }
}
