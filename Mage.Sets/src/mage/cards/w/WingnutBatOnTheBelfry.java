package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllianceAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class WingnutBatOnTheBelfry extends CardImpl {

    public WingnutBatOnTheBelfry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Alliance -- Whenever another creature you control enters, Wingnut gains your choice of flying, menace, or haste until end of turn.
        this.addAbility(new AllianceAbility(
            new GainsChoiceOfAbilitiesEffect(
                GainsChoiceOfAbilitiesEffect.TargetType.Source,
                FlyingAbility.getInstance(),
                new MenaceAbility(false),
                HasteAbility.getInstance()
            )
        ));

        // Whenever Wingnut attacks, each other attacking creature gets +1/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURE, true)));
    }

    private WingnutBatOnTheBelfry(final WingnutBatOnTheBelfry card) {
        super(card);
    }

    @Override
    public WingnutBatOnTheBelfry copy() {
        return new WingnutBatOnTheBelfry(this);
    }
}
