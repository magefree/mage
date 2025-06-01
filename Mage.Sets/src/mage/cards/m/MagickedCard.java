package mage.cards.m;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagickedCard extends CardImpl {

    public MagickedCard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.nightCard = true;
        this.color.setBlue(true);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private MagickedCard(final MagickedCard card) {
        super(card);
    }

    @Override
    public MagickedCard copy() {
        return new MagickedCard(this);
    }
}
