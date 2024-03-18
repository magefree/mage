package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.*;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SoldierTokenWithHaste;
import mage.target.common.TargetOpponent;

/**
 * @author Cguy7777
 */
public final class CaesarLegionsEmperor extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Creature tokens you control", xValue);

    public CaesarLegionsEmperor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you attack, you may sacrifice another creature. When you do, choose two --
        // * Create two 1/1 red and white Soldier creature tokens with haste that are tapped and attacking.
        ReflexiveTriggeredAbility triggeredAbility = new ReflexiveTriggeredAbility(
                new CreateTokenEffect(new SoldierTokenWithHaste(), 2, true, true), false);
        triggeredAbility.getModes().setMinModes(2);
        triggeredAbility.getModes().setMaxModes(2);

        // * You draw a card and you lose 1 life.
        Mode drawMode = new Mode(new DrawCardSourceControllerEffect(1, "you"));
        drawMode.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        triggeredAbility.addMode(drawMode);

        // * Caesar, Legion's Emperor deals damage equal to the number of creature tokens you control to target opponent.
        Mode damageMode = new Mode(new DamageTargetEffect(xValue)
                .setText("{this} deals damage equal to the number of creature tokens you control to target opponent"));
        damageMode.addTarget(new TargetOpponent());
        triggeredAbility.addMode(damageMode);
        triggeredAbility.addHint(hint);

        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DoWhenCostPaid(
                        triggeredAbility,
                        new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE),
                        "Sacrifice another creature?"),
                1).addHint(hint));
    }

    private CaesarLegionsEmperor(final CaesarLegionsEmperor card) {
        super(card);
    }

    @Override
    public CaesarLegionsEmperor copy() {
        return new CaesarLegionsEmperor(this);
    }
}
