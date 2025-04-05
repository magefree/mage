package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.FlurryAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BirdToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WingbladeDisciple extends CardImpl {

    public WingbladeDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Flurry -- Whenever you cast your second spell each turn, create a 1/1 white Bird creature token with flying.
        this.addAbility(new FlurryAbility(new CreateTokenEffect(new BirdToken())));
    }

    private WingbladeDisciple(final WingbladeDisciple card) {
        super(card);
    }

    @Override
    public WingbladeDisciple copy() {
        return new WingbladeDisciple(this);
    }
}
