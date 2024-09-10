package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Electrozoa extends CardImpl {

    public Electrozoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.JELLYFISH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Electrozoa enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // At the beginning of your precombat main phase, tap Electrozoa unless you pay {E}.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new TapSourceUnlessPaysEffect(new PayEnergyCost(1)), TargetController.YOU, false
        ));
    }

    private Electrozoa(final Electrozoa card) {
        super(card);
    }

    @Override
    public Electrozoa copy() {
        return new Electrozoa(this);
    }
}
