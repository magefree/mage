package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.DrakeToken;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author amoscatelli
 */
public final class AlandraSkyDreamer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.DRAKE, "Drakes");

    public AlandraSkyDreamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you draw your second card earch turn, create a 2/2 blue Drake creature token with flying.
        this.addAbility(
                new DrawNthCardTriggeredAbility(
                        new CreateTokenEffect(
                                new DrakeToken()
                        ),
                        false,
                        2
                )
        );

        // Whenever you draw your fifth card each turn, Alandra, Sky Dreamer and Drakes you control each get +X/+X until end of turn, where X is the number of cards in your hand.
        DrawNthCardTriggeredAbility drawNthCardTriggeredAbility = new DrawNthCardTriggeredAbility(
                new BoostSourceEffect(
                        CardsInControllerHandCount.instance,
                        CardsInControllerHandCount.instance,
                        Duration.EndOfTurn,
                        true
                ).setText("{this}"),
                false,
                5
        );
        drawNthCardTriggeredAbility.addEffect(
                new BoostControlledEffect(
                        CardsInControllerHandCount.instance,
                        CardsInControllerHandCount.instance,
                        Duration.EndOfTurn,
                        filter,
                        false,
                        true
                ).setText("and Drakes you control each get +X/+X until end of turn, where X is the number of cards in your hand")
        );
        this.addAbility(
                drawNthCardTriggeredAbility
        );

    }

    private AlandraSkyDreamer(final AlandraSkyDreamer card) {
        super(card);
    }

    @Override
    public AlandraSkyDreamer copy() {
        return new AlandraSkyDreamer(this);
    }
}
