package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.DescendedThisTurnCondition;
import mage.abilities.dynamicvalue.common.DescendedThisTurnCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.FungusCantBlockToken;
import mage.watchers.common.DescendedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BroodrageMycoid extends CardImpl {

    public BroodrageMycoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if you descended this turn, create a 1/1 black Fungus creature token with "This creature can't block."
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new FungusCantBlockToken()),
                TargetController.YOU, DescendedThisTurnCondition.instance, false
        ).addHint(DescendedThisTurnCount.getHint()), new DescendedWatcher());
    }

    private BroodrageMycoid(final BroodrageMycoid card) {
        super(card);
    }

    @Override
    public BroodrageMycoid copy() {
        return new BroodrageMycoid(this);
    }
}
