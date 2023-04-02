package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.common.DrawCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.WaylayToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheCouncilOfFour extends CardImpl {

    public TheCouncilOfFour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(8);

        // Whenever a player draws their second card during their turn, you draw a card.
        this.addAbility(new DrawCardTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
                        .setText("you draw a card"),
                false, TargetController.ACTIVE, 2
        ));

        // Whenever a player casts their second spell during their turn, you create a 2/2 white Knight creature token.
        this.addAbility(new CastSecondSpellTriggeredAbility(
                new CreateTokenEffect(new WaylayToken())
                        .setText("you create a 2/2 white Knight creature token"),
                TargetController.ACTIVE
        ));
    }

    private TheCouncilOfFour(final TheCouncilOfFour card) {
        super(card);
    }

    @Override
    public TheCouncilOfFour copy() {
        return new TheCouncilOfFour(this);
    }
}
