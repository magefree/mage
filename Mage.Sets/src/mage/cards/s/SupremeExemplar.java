
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ChampionAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class SupremeExemplar extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Elemental");

    static {
        filter.add(SubType.ELEMENTAL.getPredicate());
    }

    public SupremeExemplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Champion an Elemental
        this.addAbility(new ChampionAbility(this, SubType.ELEMENTAL));
    }

    private SupremeExemplar(final SupremeExemplar card) {
        super(card);
    }

    @Override
    public SupremeExemplar copy() {
        return new SupremeExemplar(this);
    }
}