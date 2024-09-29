package mage.cards.g;

import mage.MageInt;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Gremlin11Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GremlinTamer extends CardImpl {

    public GremlinTamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Eerie - Whenever an enchantment you control enters and whenever you fully unlock a Room, create a 1/1 red Gremlin creature token.
        this.addAbility(new EerieAbility(new CreateTokenEffect(new Gremlin11Token())));
    }

    private GremlinTamer(final GremlinTamer card) {
        super(card);
    }

    @Override
    public GremlinTamer copy() {
        return new GremlinTamer(this);
    }
}
