
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ChampionAbility;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ChangelingTitan extends CardImpl {

    public ChangelingTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Champion a creature
        this.addAbility(new ChampionAbility(this));
    }

    private ChangelingTitan(final ChangelingTitan card) {
        super(card);
    }

    @Override
    public ChangelingTitan copy() {
        return new ChangelingTitan(this);
    }
}
