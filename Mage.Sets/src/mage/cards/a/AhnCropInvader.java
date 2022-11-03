package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AhnCropInvader extends CardImpl {

    public AhnCropInvader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as it's your turn, Ahn-Crop Invader has first strike.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                FirstStrikeAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ), MyTurnCondition.instance,
                        "As long as it's your turn, "
                                + "{this} has first strike."
                )
        ).addHint(MyTurnHint.instance));

        // {1}, Sacrifice another creature: Ahn-Crop Invader gets +2/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        this.addAbility(ability);
    }

    private AhnCropInvader(final AhnCropInvader card) {
        super(card);
    }

    @Override
    public AhnCropInvader copy() {
        return new AhnCropInvader(this);
    }
}
