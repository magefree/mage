package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnhartDisciple extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HUMAN, "another Human");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public DawnhartDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another Human enters the battlefield under your control, Dawnhart Disciple gets +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), filter
        ));
    }

    private DawnhartDisciple(final DawnhartDisciple card) {
        super(card);
    }

    @Override
    public DawnhartDisciple copy() {
        return new DawnhartDisciple(this);
    }
}
