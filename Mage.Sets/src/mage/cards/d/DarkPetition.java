
package mage.cards.d;

import java.util.UUID;
import mage.Mana;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class DarkPetition extends CardImpl {

    public DarkPetition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Search your library for a card and put that card into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false, true));

        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, add {B}{B}{B}.
        Effect effect = new ConditionalOneShotEffect(new AddManaToManaPoolSourceControllerEffect(Mana.BlackMana(3)),
                SpellMasteryCondition.instance, "<br><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, add {B}{B}{B}");
        this.getSpellAbility().addEffect(effect);

    }

    private DarkPetition(final DarkPetition card) {
        super(card);
    }

    @Override
    public DarkPetition copy() {
        return new DarkPetition(this);
    }
}
