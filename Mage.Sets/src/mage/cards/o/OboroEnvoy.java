package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OboroEnvoy extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(CardsInControllerHandCount.instance);

    public OboroEnvoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.MOONFOLK, SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}, Return a land you control to its owner's hand: Target creature gets -X/-0 until end of turn, where X is the number of cards in your hand.
        Effect effect = new BoostTargetEffect(xValue, StaticValue.get(0), Duration.EndOfTurn);
        effect.setText("Target creature gets -X/-0 until end of turn, where X is the number of cards in your hand");
        Ability ability = new SimpleActivatedAbility(effect, new GenericManaCost(2));
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private OboroEnvoy(final OboroEnvoy card) {
        super(card);
    }

    @Override
    public OboroEnvoy copy() {
        return new OboroEnvoy(this);
    }
}
