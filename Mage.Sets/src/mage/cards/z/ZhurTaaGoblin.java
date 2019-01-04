package mage.cards.z;

import mage.MageInt;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZhurTaaGoblin extends CardImpl {

    public ZhurTaaGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Riot
        this.addAbility(new RiotAbility());
    }

    private ZhurTaaGoblin(final ZhurTaaGoblin card) {
        super(card);
    }

    @Override
    public ZhurTaaGoblin copy() {
        return new ZhurTaaGoblin(this);
    }
}
