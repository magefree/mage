package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.ExpendTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JunkbladeBruiser extends CardImpl {

    public JunkbladeBruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R/G}{R/G}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you expend 4, Junkblade Bruiser gets +2/+1 until end of turn.
        this.addAbility(new ExpendTriggeredAbility(new BoostSourceEffect(2, 1, Duration.EndOfTurn), 4));
    }

    private JunkbladeBruiser(final JunkbladeBruiser card) {
        super(card);
    }

    @Override
    public JunkbladeBruiser copy() {
        return new JunkbladeBruiser(this);
    }
}
