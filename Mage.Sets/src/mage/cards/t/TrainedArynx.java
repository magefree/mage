package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrainedArynx extends CardImpl {

    public TrainedArynx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Trained Arynx attacks while saddled, it gains first strike until end of turn. Scry 1.
        Ability ability = new AttacksWhileSaddledTriggeredAbility(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("it gains first strike until end of turn"));
        ability.addEffect(new ScryEffect(1));
        this.addAbility(ability);

        // Saddle 2
        this.addAbility(new SaddleAbility(2));
    }

    private TrainedArynx(final TrainedArynx card) {
        super(card);
    }

    @Override
    public TrainedArynx copy() {
        return new TrainedArynx(this);
    }
}
