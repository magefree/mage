package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.WhiteBlackSpiritToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBrokenSky extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public TheBrokenSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.color.setWhite(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // Creature tokens you control get +1/+0 and have lifelink.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("and have lifelink"));
        this.addAbility(ability);

        // At the beginning of your end step, create a 1/1 white and black Spirit creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new WhiteBlackSpiritToken()),
                TargetController.YOU, false
        ));
    }

    private TheBrokenSky(final TheBrokenSky card) {
        super(card);
    }

    @Override
    public TheBrokenSky copy() {
        return new TheBrokenSky(this);
    }
}
