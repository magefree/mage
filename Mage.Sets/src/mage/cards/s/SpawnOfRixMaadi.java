
package mage.cards.s;
 
import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.UnleashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
 
/**
 *
 * @author LevelX2
 */
public final class SpawnOfRixMaadi extends CardImpl {
 
    public SpawnOfRixMaadi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{R}");
        this.subtype.add(SubType.HORROR);
 


        this.power = new MageInt(5);
        this.toughness = new MageInt(3);
 
        // Unleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)
        this.addAbility(new UnleashAbility());
    }
 
    private SpawnOfRixMaadi(final SpawnOfRixMaadi card) {
        super(card);
    }
 
    @Override
    public SpawnOfRixMaadi copy() {
        return new SpawnOfRixMaadi(this);
    }
}