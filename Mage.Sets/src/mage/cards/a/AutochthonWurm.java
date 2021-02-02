
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class AutochthonWurm extends CardImpl {

    public AutochthonWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{10}{G}{G}{G}{W}{W}");
        this.subtype.add(SubType.WURM);


        this.power = new MageInt(9);
        this.toughness = new MageInt(14);

        // Convoke (Each creature you tap while casting this spell reduces its cost by {1} or by one mana of that creature's color.)
        this.addAbility(new ConvokeAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private AutochthonWurm(final AutochthonWurm card) {
        super(card);
    }

    @Override
    public AutochthonWurm copy() {
        return new AutochthonWurm(this);
    }
}
