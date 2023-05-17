package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class BlackbloomRogue extends ModalDoubleFacedCard {

    public BlackbloomRogue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ROGUE}, "{2}{B}",
                "Blackbloom Bog", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Blackbloom Rogue {2}{B}
        // Creature — Human Rogue
        this.getLeftHalfCard().setPT(new MageInt(2), new MageInt(3));

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility());

        // Blackbloom Rogue gets +3/+0 as long as an opponent has eight or more cards in their graveyard.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield),
                CardsInOpponentGraveyardCondition.EIGHT, "{this} gets +3/+0 as long as " +
                "an opponent has eight or more cards in their graveyard"
        )).addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint()));

        // 2.
        // Blackbloom Bog
        // Land

        // Blackbloom Bog enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.getRightHalfCard().addAbility(new BlackManaAbility());
    }

    private BlackbloomRogue(final BlackbloomRogue card) {
        super(card);
    }

    @Override
    public BlackbloomRogue copy() {
        return new BlackbloomRogue(this);
    }
}
