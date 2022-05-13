
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ChampionAbility;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ChangelingHero extends CardImpl {

    public ChangelingHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Changeling
        this.addAbility(new ChangelingAbility());
        
        // Champion a creature
        this.addAbility(new ChampionAbility(this));
        
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private ChangelingHero(final ChangelingHero card) {
        super(card);
    }

    @Override
    public ChangelingHero copy() {
        return new ChangelingHero(this);
    }
}
