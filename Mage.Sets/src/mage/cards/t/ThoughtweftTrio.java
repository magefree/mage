
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.keyword.ChampionAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ThoughtweftTrio extends CardImpl {

    public ThoughtweftTrio(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        
        // Champion a Kithkin
        this.addAbility(new ChampionAbility(this, SubType.KITHKIN));
        
        // Thoughtweft Trio can block any number of creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect(0)));
    }

    private ThoughtweftTrio(final ThoughtweftTrio card) {
        super(card);
    }

    @Override
    public ThoughtweftTrio copy() {
        return new ThoughtweftTrio(this);
    }
}
