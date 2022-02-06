
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AnotherSpiritToken;

/**
 * @author Loki
 */
public final class OyobiWhoSplitTheHeavens extends CardImpl {

    public OyobiWhoSplitTheHeavens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a Spirit or Arcane spell, create a 3/3 white Spirit creature token with flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new AnotherSpiritToken()), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false));
    }

    private OyobiWhoSplitTheHeavens(final OyobiWhoSplitTheHeavens card) {
        super(card);
    }

    @Override
    public OyobiWhoSplitTheHeavens copy() {
        return new OyobiWhoSplitTheHeavens(this);
    }
}
