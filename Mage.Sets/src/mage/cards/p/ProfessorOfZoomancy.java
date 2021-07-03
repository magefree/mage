package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Pest11GainLifeToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProfessorOfZoomancy extends CardImpl {

    public ProfessorOfZoomancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Professor of Zoomancy enters the battlefield, create a 1/1 black and green Pest creature token with "When this creature dies, you gain 1 life."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Pest11GainLifeToken())));
    }

    private ProfessorOfZoomancy(final ProfessorOfZoomancy card) {
        super(card);
    }

    @Override
    public ProfessorOfZoomancy copy() {
        return new ProfessorOfZoomancy(this);
    }
}
