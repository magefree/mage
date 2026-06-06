package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GumdropPoisoner extends AdventureCard {

    private static final DynamicValue xValue = new SignInversionDynamicValue(ControllerGainedLifeCount.instance);

    public GumdropPoisoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARLOCK}, "{2}{B}",
                "Tempt with Treats",
                new CardType[]{CardType.INSTANT}, "{B}");

        // Gumdrop Poisoner
        this.getLeftHalfCard().setPT(3, 2);

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // When Gumdrop Poisoner enters the battlefield, up to one target creature gets -X/-X until end of turn, where X is the amount of life you gained this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(xValue, xValue)
                .setText("up to one target creature gets -X/-X until end of turn, where X is the amount of life you gained this turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.addWatcher(new PlayerGainedLifeWatcher());
        this.getLeftHalfCard().addAbility(ability.addHint(ControllerGainedLifeCount.getHint()));

        // Tempt with Treats
        // Create a Food token.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken()));

        finalizeCard();
    }

    private GumdropPoisoner(final GumdropPoisoner card) {
        super(card);
    }

    @Override
    public GumdropPoisoner copy() {
        return new GumdropPoisoner(this);
    }
}
