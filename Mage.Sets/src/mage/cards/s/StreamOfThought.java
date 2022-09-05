package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StreamOfThought extends CardImpl {

    public StreamOfThought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Target player puts the top four cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new PutLibraryIntoGraveTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // You shuffle up to four cards from your graveyard into your library.
        this.getSpellAbility().addEffect(new ShuffleIntoLibraryTargetEffect().setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 4));

        // Replicate {2}{U}{U}
        this.addAbility(new ReplicateAbility("{2}{U}{U}"));
    }

    private StreamOfThought(final StreamOfThought card) {
        super(card);
    }

    @Override
    public StreamOfThought copy() {
        return new StreamOfThought(this);
    }
}
