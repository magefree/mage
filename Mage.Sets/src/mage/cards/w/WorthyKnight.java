package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WorthyKnight extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Knight spell");

    static {
        filter.add(SubType.KNIGHT.getPredicate());
    }

    public WorthyKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a Knight spell, create a 1/1 white Human creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new HumanToken()), filter, false
        ));
    }

    private WorthyKnight(final WorthyKnight card) {
        super(card);
    }

    @Override
    public WorthyKnight copy() {
        return new WorthyKnight(this);
    }
}
