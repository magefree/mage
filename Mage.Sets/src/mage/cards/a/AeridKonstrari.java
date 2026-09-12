package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.permanent.token.HeartwoodToken;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class AeridKonstrari extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifacts you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public AeridKonstrari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Aerid Konstrari enters or dies, create a Heartwood token.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(
            new CreateTokenEffect(new HeartwoodToken()), false
        ));

        // {6}: Create a Heartwood token. Then Aerid Konstrari gets +X/+0 until end of turn, where X is the number of artifacts you control.
        Ability ability = new SimpleActivatedAbility(
            new CreateTokenEffect(new HeartwoodToken()),
            new ManaCostsImpl<>("{6}")
        );
        ability.addEffect(new BoostSourceEffect(
            xValue, StaticValue.get(0), Duration.EndOfTurn
        ).setText("Then {this} gets +X/+0 until end of turn, where X is the number of artifacts you control"));
        this.addAbility(ability.addHint(new ValueHint("Artifacts you control", xValue)));
    }

    private AeridKonstrari(final AeridKonstrari card) {
        super(card);
    }

    @Override
    public AeridKonstrari copy() {
        return new AeridKonstrari(this);
    }
}
