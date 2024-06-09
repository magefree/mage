package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrimInitiate extends CardImpl {

    public GrimInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When Grim Initiate dies, amass 1.
        this.addAbility(new DiesSourceTriggeredAbility(new AmassEffect(1, SubType.ZOMBIE)));
    }

    private GrimInitiate(final GrimInitiate card) {
        super(card);
    }

    @Override
    public GrimInitiate copy() {
        return new GrimInitiate(this);
    }
}
