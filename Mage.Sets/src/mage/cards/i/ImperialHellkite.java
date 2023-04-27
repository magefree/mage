
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterBySubtypeCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class ImperialHellkite extends CardImpl {
    
    public ImperialHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Morph {6}{R}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{6}{R}{R}")));
        
        // When Imperial Hellkite is turned face up, you may search your library for a Dragon card, reveal it, and put it into your hand. If you do, shuffle your library.
        Effect effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, new FilterBySubtypeCard(SubType.DRAGON)), true, true);
        effect.setText("you may search your library for a Dragon card, reveal it, and put it into your hand. If you do, shuffle");
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(effect));
    }

    private ImperialHellkite(final ImperialHellkite card) {
        super(card);
    }

    @Override
    public ImperialHellkite copy() {
        return new ImperialHellkite(this);
    }
}
