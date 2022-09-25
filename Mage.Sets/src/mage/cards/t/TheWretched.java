package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class TheWretched extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures blocking {this}");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
    }

    public TheWretched(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // At end of combat, gain control of all creatures blocking The Wretched for as long as you control The Wretched.
        this.addAbility(new EndOfCombatTriggeredAbility(new GainControlAllEffect(Duration.WhileControlled, filter), false));
    }

    private TheWretched(final TheWretched card) {
        super(card);
    }

    @Override
    public TheWretched copy() {
        return new TheWretched(this);
    }
}
