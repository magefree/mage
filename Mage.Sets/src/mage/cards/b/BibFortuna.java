
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ShuffleLibrarySourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Styxo
 */
public final class BibFortuna extends CardImpl {

    public BibFortuna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TWILEK, SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Bib Fortuna enters the battlefield search your library for a card then shuffle your library and put in on top. You lose 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(), false), false);
        ability.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addAbility(ability);

        // When Bib Fortuna dies shuffle your library.
        this.addAbility(new DiesSourceTriggeredAbility(new ShuffleLibrarySourceEffect()));
    }

    private BibFortuna(final BibFortuna card) {
        super(card);
    }

    @Override
    public BibFortuna copy() {
        return new BibFortuna(this);
    }
}
