package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class WitchKingSkyScourge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wraiths");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.WRAITH.getPredicate());
    }
    public WitchKingSkyScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WRAITH);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you attack with one or more Wraiths, exile the top X cards of your library, where X is their total power. You may play those cards this turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(Zone.BATTLEFIELD,
                new ExileTopXMayPlayUntilEndOfTurnEffect(TotalTargetsPowerValue.instance)
                        .setText("exile the top X cards of your library, where X is their total power. You may play those cards this turn.")
                , 1, filter, true));

        // Undying
        this.addAbility(new UndyingAbility());

    }

    private WitchKingSkyScourge(final WitchKingSkyScourge card) {
        super(card);
    }

    @Override
    public WitchKingSkyScourge copy() {
        return new WitchKingSkyScourge(this);
    }
}

enum TotalTargetsPowerValue implements DynamicValue {
    instance;

    @Override
    public TotalTargetsPowerValue copy() {
        return instance;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int totalPower = 0;
        Cards cards = new CardsImpl(effect.getTargetPointer().getTargets(game, sourceAbility));
        for (UUID targetId : cards) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }
            totalPower += permanent.getPower().getValue();
        }
        return totalPower;
    }

    @Override
    public String getMessage() {
        return "their total power";
    }

    @Override
    public String toString() {
        return "X";
    }
}
