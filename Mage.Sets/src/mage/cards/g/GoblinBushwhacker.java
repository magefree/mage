package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class GoblinBushwhacker extends CardImpl {

    public GoblinBushwhacker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kicker {R} (You may pay an additional {R} as you cast this spell.)
        this.addAbility(new KickerAbility("{R}"));

        // When this creature enters, if it was kicked, creatures you control get +1/+0 and gain haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn)
                        .setText("creatures you control get +1/+0")).withInterveningIf(KickedCondition.ONCE);
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("and gain haste until end of turn"));
        this.addAbility(ability);
    }

    private GoblinBushwhacker(final GoblinBushwhacker card) {
        super(card);
    }

    @Override
    public GoblinBushwhacker copy() {
        return new GoblinBushwhacker(this);
    }
}
