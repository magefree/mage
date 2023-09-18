package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ChitteringHost extends MeldCard {

    public ChitteringHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // When Chittering Host enters the battlefield, other creatures you control get +1/+0 and gain menace until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, true
        ).setText("other creatures you control get +1/+0"), false);
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        ).setText("and gain menace until end of turn"));
        this.addAbility(ability);
    }

    private ChitteringHost(final ChitteringHost card) {
        super(card);
    }

    @Override
    public ChitteringHost copy() {
        return new ChitteringHost(this);
    }
}
