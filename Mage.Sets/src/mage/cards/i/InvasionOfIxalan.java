package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfIxalan extends TransformingDoubleFacedCard {

    public InvasionOfIxalan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{1}{G}",
                "Belligerent Regisaur",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DINOSAUR}, "G"
        );

        // Invasion of Ixalan
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Ixalan enters the battlefield, look at the top five cards of your library. You may reveal a permanent card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_A_PERMANENT,
                PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));

        // Belligerent Regisaur
        this.getRightHalfCard().setPT(4, 3);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever you cast a spell, Belligerent Regisaur gains indestructible until end of turn.
        this.getRightHalfCard().addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), false));
    }

    private InvasionOfIxalan(final InvasionOfIxalan card) {
        super(card);
    }

    @Override
    public InvasionOfIxalan copy() {
        return new InvasionOfIxalan(this);
    }
}
