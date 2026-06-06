package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.effects.common.MeldEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class GrafRats extends MeldCard {

    private static final Condition condition = new MeldCondition("Midnight Scavengers");

    public GrafRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.RAT}, "{1}{B}",
                "Chittering Host",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.HORROR}, "");
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.meldsWithClazz = mage.cards.m.MidnightScavengers.class;

        // Graf Rats
        this.getLeftHalfCard().setPT(2, 1);

        // At the beginning of combat on your turn, if you both own and control Graf Rats and a creature named Midnight Scavengers, exile them, then meld them into Chittering Host.
        this.getLeftHalfCard().addAbility(new BeginningOfCombatTriggeredAbility(new MeldEffect(
                "Midnight Scavengers", "Chittering Host"
        )).withInterveningIf(condition));

        // Chittering Host
        this.getRightHalfCard().setPT(5, 6);

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());

        // When Chittering Host enters the battlefield, other creatures you control get +1/+0 and gain menace until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, true
        ).setText("other creatures you control get +1/+0"), false);
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        ).setText("and gain menace until end of turn"));
        this.getRightHalfCard().addAbility(ability);
    }

    private GrafRats(final GrafRats card) {
        super(card);
    }

    @Override
    public GrafRats copy() {
        return new GrafRats(this);
    }
}
