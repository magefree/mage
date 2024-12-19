package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromLibrarySourceTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class Narcomoeba extends CardImpl {

    public Narcomoeba(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Narcomoeba is put into your graveyard from your library, you may put it onto the battlefield.
        this.addAbility(new PutIntoGraveFromLibrarySourceTriggeredAbility(
                new ReturnSourceFromGraveyardToBattlefieldEffect().setText("put it onto the battlefield"), true
        ));
    }

    private Narcomoeba(final Narcomoeba card) {
        super(card);
    }

    @Override
    public Narcomoeba copy() {
        return new Narcomoeba(this);
    }
}
