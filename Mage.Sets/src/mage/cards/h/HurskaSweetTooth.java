package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HurskaSweetTooth extends CardImpl {

    public HurskaSweetTooth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Hurska Sweet-Tooth attacks, create a Food token.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Whenever you gain life, you may pay {G/W}. When you do, target creature gets +X/+X until end of turn, where X is the amount of life you gained.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new BoostTargetEffect(SavedGainedLifeValue.MANY, SavedGainedLifeValue.MANY)
                        .setText("target creature gets +X/+X until end of turn, where X is the amount of life you gained"), false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new DoWhenCostPaid(ability, new ManaCostsImpl<>("{G/W}"), "Pay {G/W}?")
        ));
    }

    private HurskaSweetTooth(final HurskaSweetTooth card) {
        super(card);
    }

    @Override
    public HurskaSweetTooth copy() {
        return new HurskaSweetTooth(this);
    }
}
