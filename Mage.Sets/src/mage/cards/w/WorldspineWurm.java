
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WurmToken2;

/**
 *
 * @author Plopman
 */
public final class WorldspineWurm extends CardImpl {

    public WorldspineWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}{G}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(15);
        this.toughness = new MageInt(15);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Worldspine Wurm dies, create three 5/5 green Wurm creature tokens with trample.
        this.addAbility(new DiesTriggeredAbility(new CreateTokenEffect(new WurmToken2(), 3)));

        // When Worldspine Wurm is put into a graveyard from anywhere, shuffle it into its owner's library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect()));
    }

    public WorldspineWurm(final WorldspineWurm card) {
        super(card);
    }

    @Override
    public WorldspineWurm copy() {
        return new WorldspineWurm(this);
    }
}
