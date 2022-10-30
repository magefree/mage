package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author stravant
 */
public final class NagaOracle extends CardImpl {

    public NagaOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Naga Oracle enters the battlefield, look at the top three cards of your library. Put any number of them into your graveyard
        // and the rest back on top of your library in any order.
        addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(3)));
    }

    private NagaOracle(final NagaOracle card) {
        super(card);
    }

    @Override
    public NagaOracle copy() {
        return new NagaOracle(this);
    }
}
