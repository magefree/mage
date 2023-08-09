
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class MasterOfPearls extends CardImpl {

    public MasterOfPearls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Morph {3}{W}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}{W}{W}")));
        // When Master of Pearls is turned face up, creatures you control get +2/+2 until end of turn.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new BoostControlledEffect(2, 2, Duration.EndOfTurn)));
    }

    private MasterOfPearls(final MasterOfPearls card) {
        super(card);
    }

    @Override
    public MasterOfPearls copy() {
        return new MasterOfPearls(this);
    }
}
