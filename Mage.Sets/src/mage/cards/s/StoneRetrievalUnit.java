package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StoneRetrievalUnit extends CardImpl {

    public StoneRetrievalUnit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Stone Retrieval Unit enters the battlefield, create a tapped Powerstone token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new PowerstoneToken(), 1, true)
        ));
    }

    private StoneRetrievalUnit(final StoneRetrievalUnit card) {
        super(card);
    }

    @Override
    public StoneRetrievalUnit copy() {
        return new StoneRetrievalUnit(this);
    }
}
