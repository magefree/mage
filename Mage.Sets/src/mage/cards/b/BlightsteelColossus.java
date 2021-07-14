

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.common.RevealAndShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class BlightsteelColossus extends CardImpl {

    public BlightsteelColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{12}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(11);
        this.toughness = new MageInt(11);
        
        // Trample, infect
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(InfectAbility.getInstance());

        // Blightsteel Colossus is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());
        
        // If Blightsteel Colossus would be put into a graveyard from anywhere, reveal Blightsteel Colossus and shuffle it into its owner's library instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new RevealAndShuffleIntoLibrarySourceEffect()));
    }

    private BlightsteelColossus(final BlightsteelColossus card) {
        super(card);
    }

    @Override
    public BlightsteelColossus copy() {
        return new BlightsteelColossus(this);
    }

}
