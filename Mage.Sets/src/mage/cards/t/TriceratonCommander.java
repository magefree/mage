package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.DinosaurSoldierToken;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class TriceratonCommander extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.DINOSAUR, "Dinosaurs");

    public TriceratonCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{X}{W}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, Dinosaurs you control other than this creature get +1/+1 and gain flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(
            new BoostControlledEffect(1, 1, Duration.EndOfTurn, filter, true)
                .setText("Dinosaurs you control other than this creature get +1/+1"),
            false
        );
        ability.addEffect(
            new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, filter, true)
                .setText("and gain flying until end of turn")
        );
        this.addAbility(ability);

        // When this creature enters, create X 2/2 white Dinosaur Soldier creature tokens.
        this.addAbility(new EntersBattlefieldAbility(new CreateTokenEffect(new DinosaurSoldierToken(), GetXValue.instance)));
    }

    private TriceratonCommander(final TriceratonCommander card) {
        super(card);
    }

    @Override
    public TriceratonCommander copy() {
        return new TriceratonCommander(this);
    }
}
