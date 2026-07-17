package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MomoPlayfulPet extends CardImpl {

    public MomoPlayfulPet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LEMUR);
        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Momo leaves the battlefield, choose one --
        // * Create a Food token.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken()));

        // * Put a +1/+1 counter on target creature you control.
        ability.addMode(new Mode(new AddCountersTargetEffect(CounterType.P1P1.createInstance()))
                .addTarget(new TargetControlledCreaturePermanent()));

        // * Scry 2.
        ability.addMode(new Mode(new ScryEffect(2)));
        this.addAbility(ability);
    }

    private MomoPlayfulPet(final MomoPlayfulPet card) {
        super(card);
    }

    @Override
    public MomoPlayfulPet copy() {
        return new MomoPlayfulPet(this);
    }
}
