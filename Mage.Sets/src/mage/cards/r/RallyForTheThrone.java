package mage.cards.r;

import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RallyForTheThrone extends CardImpl {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE);

    public RallyForTheThrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Create two 1/1 white Human creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new HumanToken(), 2));

        // Adamant — If at least three white mana was spent to cast this spell, you gain 1 life for each creature you control.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(xValue), AdamantCondition.WHITE, "<br><i>Adamant</i> &mdash; " +
                "If at least three white mana was spent to cast this spell, " +
                "you gain 1 life for each creature you control."
        ));
    }

    private RallyForTheThrone(final RallyForTheThrone card) {
        super(card);
    }

    @Override
    public RallyForTheThrone copy() {
        return new RallyForTheThrone(this);
    }
}
