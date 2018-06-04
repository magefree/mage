
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.NameACardEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Memoricide extends CardImpl {

    public Memoricide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        // Name a nonland card. Search target player's graveyard, hand, and library for any number of cards with
        // that name and exile them. Then that player shuffles their library
        this.getSpellAbility().addEffect((new NameACardEffect(NameACardEffect.TypeOfName.NON_LAND_NAME)));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new MemoricideEffect());
    }

    public Memoricide(final Memoricide card) {
        super(card);
    }

    @Override
    public Memoricide copy() {
        return new Memoricide(this);
    }

}

class MemoricideEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    MemoricideEffect() {
        super(true, "target player's", "any number of cards with that name");
    }

    MemoricideEffect(final MemoricideEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
        return super.applySearchAndExile(game, source, cardName, targetPointer.getFirst(game, source));
    }

    @Override
    public MemoricideEffect copy() {
        return new MemoricideEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "Name a nonland card. " + super.getText(mode);
    }
}
