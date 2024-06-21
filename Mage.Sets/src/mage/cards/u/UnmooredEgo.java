package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class UnmooredEgo extends CardImpl {

    public UnmooredEgo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{B}");

        // Choose a card name. Search target opponent's graveyard, hand, and library for up to four cards with that name and exile them. That player shuffles their library, then draws a card for each card exiled from their hand this way.
        this.getSpellAbility().addEffect((new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL)));
        this.getSpellAbility().addEffect(new UnmooredEgoEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private UnmooredEgo(final UnmooredEgo card) {
        super(card);
    }

    @Override
    public UnmooredEgo copy() {
        return new UnmooredEgo(this);
    }
}

class UnmooredEgoEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    UnmooredEgoEffect() {
        super(false, "target opponent's", "up to four cards with that name", true, 4);
    }

    private UnmooredEgoEffect(final UnmooredEgoEffect effect) {
        super(effect);
    }

    @Override
    public UnmooredEgoEffect copy() {
        return new UnmooredEgoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        return applySearchAndExile(game, source, cardName, getTargetPointer().getFirst(game, source));
    }
}
