package mage.cards.s;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class SeaGateRestoration extends ModalDoubleFacedCard {

    private static final DynamicValue xValue = new AdditiveDynamicValue(CardsInControllerHandCount.instance, StaticValue.get(1));

    public SeaGateRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{4}{U}{U}{U}",
                "Sea Gate, Reborn", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Sea Gate Restoration
        // Sorcery

        // Draw cards equal to the number of cards in your hand plus one. You have no maximum hand size for the rest of the game.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(xValue)
                .setText("Draw cards equal to the number of cards in your hand plus one."));
        this.getLeftHalfCard().getSpellAbility().addEffect(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.EndOfGame,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        ));

        // 2.
        // Sea Gate, Reborn
        // Land

        // As Sea Gate, Reborn enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {U}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());
    }

    private SeaGateRestoration(final SeaGateRestoration card) {
        super(card);
    }

    @Override
    public SeaGateRestoration copy() {
        return new SeaGateRestoration(this);
    }
}
