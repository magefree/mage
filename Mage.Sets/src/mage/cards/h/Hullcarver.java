package mage.cards.h;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Hullcarver extends CardImpl {

    public Hullcarver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private Hullcarver(final Hullcarver card) {
        super(card);
    }

    @Override
    public Hullcarver copy() {
        return new Hullcarver(this);
    }
}
