package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkylineScout extends CardImpl {

    public SkylineScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Skyline Scout attacks, you may pay {1}{W}. If you do, it gains flying until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn)
                        .setText("it gains flying until end of turn"),
                new ManaCostsImpl<>("{1}{W}")
        ), false));
    }

    private SkylineScout(final SkylineScout card) {
        super(card);
    }

    @Override
    public SkylineScout copy() {
        return new SkylineScout(this);
    }
}
