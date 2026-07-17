package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElephantMandrill extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("artifact your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Artifacts your opponents control", xValue);

    public ElephantMandrill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.MONKEY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When this creature enters, each player creates a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenAllEffect(new FoodToken(), TargetController.EACH_PLAYER)
        ));

        // At the beginning of combat on your turn, this creature gets +1/+1 until end of turn for each artifact your opponents control.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn)
        ).addHint(hint));
    }

    private ElephantMandrill(final ElephantMandrill card) {
        super(card);
    }

    @Override
    public ElephantMandrill copy() {
        return new ElephantMandrill(this);
    }
}
