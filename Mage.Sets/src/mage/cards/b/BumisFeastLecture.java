package mage.cards.b;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BumisFeastLecture extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.FOOD, "twice the number of Foods you control"), 2
    );
    private static final Hint hint = new ValueHint(
            "Foods you control", new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.FOOD))
    );

    public BumisFeastLecture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        this.subtype.add(SubType.LESSON);

        // Create a Food token. Then earthbend X, where X is twice the number of Foods you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken()));
        this.getSpellAbility().addEffect(new EarthbendTargetEffect(xValue).concatBy("Then"));
        this.getSpellAbility().addTarget(new TargetControlledLandPermanent());
        this.getSpellAbility().addHint(hint);
    }

    private BumisFeastLecture(final BumisFeastLecture card) {
        super(card);
    }

    @Override
    public BumisFeastLecture copy() {
        return new BumisFeastLecture(this);
    }
}
