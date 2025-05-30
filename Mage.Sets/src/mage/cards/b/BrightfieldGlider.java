package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SaddleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrightfieldGlider extends CardImpl {

    public BrightfieldGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.POSSUM);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever this creature attacks while saddled, it gets +1/+2 and gains flying until end of turn.
        Ability ability = new AttacksWhileSaddledTriggeredAbility(new BoostSourceEffect(
                1, 2, Duration.EndOfTurn
        ).setText("it gets +1/+2"));
        ability.addEffect(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains flying until end of turn"));
        this.addAbility(ability);

        // Saddle 3
        this.addAbility(new SaddleAbility(3));
    }

    private BrightfieldGlider(final BrightfieldGlider card) {
        super(card);
    }

    @Override
    public BrightfieldGlider copy() {
        return new BrightfieldGlider(this);
    }
}
