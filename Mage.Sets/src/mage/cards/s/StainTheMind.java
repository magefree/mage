package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StainTheMind extends CardImpl {

    public StainTheMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Convoke
        this.addAbility(new ConvokeAbility());
        // Name a nonland card. Search target player's graveyard, hand, and library for any number of card's with that name and exile them. Then that player shuffles their library.
        this.getSpellAbility().addEffect((new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_LAND_NAME)));
        this.getSpellAbility().addEffect(new StainTheMindEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private StainTheMind(final StainTheMind card) {
        super(card);
    }

    @Override
    public StainTheMind copy() {
        return new StainTheMind(this);
    }
}

class StainTheMindEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public StainTheMindEffect() {
        super(true, "target player's", "any number of cards with that name");
    }

    private StainTheMindEffect(final StainTheMindEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (cardName == null) {
            return false;
        }
        return super.applySearchAndExile(game, source, cardName, targetPointer.getFirst(game, source));
    }

    @Override
    public StainTheMindEffect copy() {
        return new StainTheMindEffect(this);
    }

}
