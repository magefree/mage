

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.common.RevealAndShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class DarksteelColossus extends CardImpl {

    public DarksteelColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{11}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(11);
        this.toughness = new MageInt(11);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Darksteel Colossus is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());

        // If Darksteel Colossus would be put into a graveyard from anywhere, reveal Darksteel Colossus and shuffle it into its owner's library instead.        
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new RevealAndShuffleIntoLibrarySourceEffect()));
    }

    private DarksteelColossus(final DarksteelColossus card) {
        super(card);
    }

    @Override
    public DarksteelColossus copy() {
        return new DarksteelColossus(this);
    }

}
