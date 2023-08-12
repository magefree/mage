package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 *
 * @author noahg
 */
public final class SerraInquisitors extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public SerraInquisitors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Serra Inquisitors blocks or becomes blocked by one or more black creatures, Serra Inquisitors gets +2/+0 until end of turn.
        this.addAbility(new BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn),
                filter, false).setReplaceRuleText(false));
    }

    private SerraInquisitors(final SerraInquisitors card) {
        super(card);
    }

    @Override
    public SerraInquisitors copy() {
        return new SerraInquisitors(this);
    }
}
