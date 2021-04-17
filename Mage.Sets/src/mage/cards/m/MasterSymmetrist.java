package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MasterSymmetrist extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature you control with power equal to its toughness");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(MasterSymmetristPredicate.instance);
    }

    public MasterSymmetrist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever a creature you control with power equal to its toughness attacks, it gains trample until end of turn.
        this.addAbility(new AttacksAllTriggeredAbility(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                "it gains trample until end of turn"
        ), false, filter, SetTargetPointer.PERMANENT, false));
    }

    private MasterSymmetrist(final MasterSymmetrist card) {
        super(card);
    }

    @Override
    public MasterSymmetrist copy() {
        return new MasterSymmetrist(this);
    }
}

enum MasterSymmetristPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getPower().getValue() == input.getToughness().getValue();
    }
}
