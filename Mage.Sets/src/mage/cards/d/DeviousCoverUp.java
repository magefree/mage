package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author TheElk801
 */
public final class DeviousCoverUp extends CardImpl {

    public DeviousCoverUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(Zone.EXILED));
        this.getSpellAbility().addTarget(new TargetSpell());

        // You may shuffle up to four target cards from your graveyard into your library.
        this.getSpellAbility().addEffect(new ShuffleIntoLibraryTargetEffect(true).setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 4));
    }

    private DeviousCoverUp(final DeviousCoverUp card) {
        super(card);
    }

    @Override
    public DeviousCoverUp copy() {
        return new DeviousCoverUp(this);
    }
}
