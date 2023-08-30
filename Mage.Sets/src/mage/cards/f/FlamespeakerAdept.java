package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FlamespeakerAdept extends CardImpl {

    public FlamespeakerAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you scry, Flamespeaker Adept gets +2/+0 and gains first strike until end of turn.
        Ability ability = new ScryTriggeredAbility(new BoostSourceEffect(
                2, 0, Duration.EndOfTurn
        ).setText("{this} gets +2/+0"));
        ability.addEffect(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        this.addAbility(ability);
    }

    private FlamespeakerAdept(final FlamespeakerAdept card) {
        super(card);
    }

    @Override
    public FlamespeakerAdept copy() {
        return new FlamespeakerAdept(this);
    }
}
