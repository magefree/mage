package mage.cards.g;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GoldRush extends CardImpl {

    private static final DynamicValue value = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.TREASURE, "Treasure you control")
    );
    private static final DynamicValue xValue = new MultipliedValue(value, 2);
    private static final Hint hint = new ValueHint("Treasure you control", value);


    public GoldRush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Create a Treasure token. Until end of turn, up to one target creature gets +2/+2 for each Treasure you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));
        this.getSpellAbility().addEffect(
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn)
                        .setText("Until end of turn, up to one target creature gets +2/+2 for each Treasure you control.")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addHint(hint);
    }

    private GoldRush(final GoldRush card) {
        super(card);
    }

    @Override
    public GoldRush copy() {
        return new GoldRush(this);
    }
}
