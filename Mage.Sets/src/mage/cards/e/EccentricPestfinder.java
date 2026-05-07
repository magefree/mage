package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.PestBlackGreenDiesToken;
import mage.watchers.common.PlayerGainedLifeWatcher;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class EccentricPestfinder extends PrepareCard {

    private static final Condition condition = YouGainedLifeCondition.getZero();

    public EccentricPestfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}", "Turn Stones", new CardType[]{CardType.SORCERY}, "{B}{G}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of each end step, if you gained life this turn, this creature becomes prepared.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            TargetController.ANY, new BecomePreparedSourceEffect(), false
        ).withInterveningIf(condition).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());

        // Turn Stones
        // Sorcery {B}{G}
        // For each opponent, you create a 1/1 black and green Pest creature token with "When this token dies, you gain 1 life."
        this.getSpellCard().getSpellAbility().addEffect(
            new CreateTokenEffect(new PestBlackGreenDiesToken(), OpponentsCount.instance)
                .setText("for each opponent, you create a 1/1 black and green Pest creature token with \"When this token dies, you gain 1 life.\"")
        );
    }

    private EccentricPestfinder(final EccentricPestfinder card) {
        super(card);
    }

    @Override
    public EccentricPestfinder copy() {
        return new EccentricPestfinder(this);
    }
}
