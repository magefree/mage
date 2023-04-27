package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoringWarplow extends CardImpl {

    public GoringWarplow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Prototype {1}{B} -- 1/1
        this.addAbility(new PrototypeAbility(this, "{1}{B}", 1, 1));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private GoringWarplow(final GoringWarplow card) {
        super(card);
    }

    @Override
    public GoringWarplow copy() {
        return new GoringWarplow(this);
    }
}
