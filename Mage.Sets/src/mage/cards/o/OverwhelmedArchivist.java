package mage.cards.o;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverwhelmedArchivist extends TransformingDoubleFacedCard {

    public OverwhelmedArchivist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{2}{U}",
                "Archive Haunt",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.WIZARD}, "U");

        // Overwhelmed Archivist
        this.getLeftHalfCard().setPT(3, 2);

        // When Overwhelmed Archivist enters the battlefield, draw a card, then discard a card.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));

        // Disturb {3}{U}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{3}{U}"));

        // Archive Haunt
        this.getRightHalfCard().setPT(2, 1);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever Archive Haunt attacks, draw a card, then discard a card.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));

        // If Archive Haunt would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private OverwhelmedArchivist(final OverwhelmedArchivist card) {
        super(card);
    }

    @Override
    public OverwhelmedArchivist copy() {
        return new OverwhelmedArchivist(this);
    }
}
