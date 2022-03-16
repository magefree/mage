package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.continuous.GainControlAllOwnedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BroodingSaurian extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nontoken permanents");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public BroodingSaurian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.LIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of each end step, each player gains control of all nontoken permanents they own.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new GainControlAllOwnedEffect(filter), TargetController.ANY, false
        ));
    }

    private BroodingSaurian(final BroodingSaurian card) {
        super(card);
    }

    @Override
    public BroodingSaurian copy() {
        return new BroodingSaurian(this);
    }
}
