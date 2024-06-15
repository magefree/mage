package mage.cards.c;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author tiera3 - based on ChardalynDragon
 * note - draftmatters ability not implemented
 */
public final class CogworkSpy extends CardImpl {

    public CogworkSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private CogworkSpy(final CogworkSpy card) {
        super(card);
    }

    @Override
    public CogworkSpy copy() {
        return new CogworkSpy(this);
    }
}
