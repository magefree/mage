package mage.cards.g;

import mage.abilities.common.PutIntoGraveFromLibrarySourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ShuffleYourGraveyardIntoLibraryEffect;
import mage.abilities.effects.common.TargetPlayerShufflesTargetCardsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInTargetPlayersGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GaeasBlessing extends CardImpl {

    public GaeasBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Target player shuffles up to three target cards from their graveyard into their library.
        this.getSpellAbility().addEffect(new TargetPlayerShufflesTargetCardsEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetCardInTargetPlayersGraveyard(3));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));

        // When Gaea's Blessing is put into your graveyard from your library, shuffle your graveyard into your library.
        this.addAbility(new PutIntoGraveFromLibrarySourceTriggeredAbility(new ShuffleYourGraveyardIntoLibraryEffect()));
    }

    private GaeasBlessing(final GaeasBlessing card) {
        super(card);
    }

    @Override
    public GaeasBlessing copy() {
        return new GaeasBlessing(this);
    }
}
