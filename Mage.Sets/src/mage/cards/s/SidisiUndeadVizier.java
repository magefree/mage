
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class SidisiUndeadVizier extends CardImpl {

    public SidisiUndeadVizier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.NAGA);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        
        // Exploit
        this.addAbility(new ExploitAbility());
        
        // When Sidisi, Undead Vizier exploits a creature, you may search your library for a card, put it into your hand, then shuffle your library.
        this.addAbility(new ExploitCreatureTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(new FilterCard("card")), false), false));
    }

    private SidisiUndeadVizier(final SidisiUndeadVizier card) {
        super(card);
    }

    @Override
    public SidisiUndeadVizier copy() {
        return new SidisiUndeadVizier(this);
    }
}
