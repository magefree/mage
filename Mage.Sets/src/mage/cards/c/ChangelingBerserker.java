
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ChampionAbility;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ChangelingBerserker extends CardImpl {

    public ChangelingBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Changeling
        this.addAbility(new ChangelingAbility());
        
        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Champion a creature
        this.addAbility(new ChampionAbility(this));
    }

    private ChangelingBerserker(final ChangelingBerserker card) {
        super(card);
    }

    @Override
    public ChangelingBerserker copy() {
        return new ChangelingBerserker(this);
    }
}
