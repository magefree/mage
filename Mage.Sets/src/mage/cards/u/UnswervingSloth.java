package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnswervingSloth extends CardImpl {

    public UnswervingSloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.SLOTH);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever this creature attacks while saddled, it gains indestructible until end of turn. Untap all creatures you control.
        Ability ability = new AttacksWhileSaddledTriggeredAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("it gains indestructible until end of turn"));
        ability.addEffect(new UntapAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURES));
        this.addAbility(ability);

        // Saddle 4
        this.addAbility(new SaddleAbility(4));
    }

    private UnswervingSloth(final UnswervingSloth card) {
        super(card);
    }

    @Override
    public UnswervingSloth copy() {
        return new UnswervingSloth(this);
    }
}
