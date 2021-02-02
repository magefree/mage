package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WurmWithTrampleToken;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class WorldspineWurm extends CardImpl {

    public WorldspineWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{8}{G}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(15);
        this.toughness = new MageInt(15);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Worldspine Wurm dies, create three 5/5 green Wurm creature tokens with trample.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new WurmWithTrampleToken(), 3)));

        // When Worldspine Wurm is put into a graveyard from anywhere, shuffle it into its owner's library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect()));
    }

    private WorldspineWurm(final WorldspineWurm card) {
        super(card);
    }

    @Override
    public WorldspineWurm copy() {
        return new WorldspineWurm(this);
    }
}
