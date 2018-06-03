
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.NameACardEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class SlaughterGames extends CardImpl {

    public SlaughterGames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{R}");

        // Slaughter Games can't be countered by spells or abilities.
        Effect effect = new CantBeCounteredSourceEffect();
        effect.setText("{this} can't be countered by spells or abilities");
        Ability ability = new SimpleStaticAbility(Zone.STACK, effect);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Name a nonland card. Search target opponent's graveyard, hand, and library for any number of cards with that name and exile them. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new NameACardEffect(NameACardEffect.TypeOfName.NON_LAND_NAME));
        this.getSpellAbility().addEffect(new SlaughterGamesEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public SlaughterGames(final SlaughterGames card) {
        super(card);
    }

    @Override
    public SlaughterGames copy() {
        return new SlaughterGames(this);
    }
}

class SlaughterGamesEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public SlaughterGamesEffect() {
        super(true, "target opponent's", "any number of cards with that name");
    }

    public SlaughterGamesEffect(final SlaughterGamesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
        return super.applySearchAndExile(game, source, cardName, targetPointer.getFirst(game, source));
    }

    @Override
    public SlaughterGamesEffect copy() {
        return new SlaughterGamesEffect(this);
    }

}
