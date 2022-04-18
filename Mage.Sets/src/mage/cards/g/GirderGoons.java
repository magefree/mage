package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RogueToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GirderGoons extends CardImpl {

    public GirderGoons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Girder Goons dies, create a tapped 2/2 black Rogue creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(
                new RogueToken(), 1, true, false
        )));

        // Blitz {3}{B}
        this.addAbility(new BlitzAbility("{3}{B}"));
    }

    private GirderGoons(final GirderGoons card) {
        super(card);
    }

    @Override
    public GirderGoons copy() {
        return new GirderGoons(this);
    }
}
