

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 * @author Loki
 */
public final class BogGnarr extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a black spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public BogGnarr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add( SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a player casts a black spell, Bog Gnarr gets +2/+2 until end of turn.
        this.addAbility(new SpellCastAllTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), filter, false));
    }

    private BogGnarr(final BogGnarr card) {
        super(card);
    }

    @Override
    public BogGnarr copy() {
        return new BogGnarr(this);
    }
}
