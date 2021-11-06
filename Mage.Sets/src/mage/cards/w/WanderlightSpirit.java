package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WanderlightSpirit extends CardImpl {

    public WanderlightSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Wanderlight Spirit can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private WanderlightSpirit(final WanderlightSpirit card) {
        super(card);
    }

    @Override
    public WanderlightSpirit copy() {
        return new WanderlightSpirit(this);
    }
}
