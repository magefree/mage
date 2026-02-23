package mage.cards.c;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaptiveWeird extends TransformingDoubleFacedCard {

    public CaptiveWeird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEIRD}, "{U}",
                "Compleated Conjurer",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.WEIRD}, "UR");

        // Captive Weird
        this.getLeftHalfCard().setPT(1, 3);

        // Defender
        this.getLeftHalfCard().addAbility(DefenderAbility.getInstance());

        // {3}{R/P}: Transform Captive Weird. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{3}{R/P}")
        ));

        // Compleated Conjurer
        this.getRightHalfCard().setPT(3, 3);

        // When this creature transforms into Compleated Conjurer, exile the top card of your library.
        // Until the end of your next turn, you may play that card.
        this.getRightHalfCard().addAbility(new TransformIntoSourceTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn)
        ));
    }

    private CaptiveWeird(final CaptiveWeird card) {
        super(card);
    }

    @Override
    public CaptiveWeird copy() {
        return new CaptiveWeird(this);
    }
}
