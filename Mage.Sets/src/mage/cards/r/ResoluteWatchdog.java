package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ResoluteWatchdog extends CardImpl {

    public ResoluteWatchdog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {1}, Sacrifice Resolute Watchdog: Target creature you control gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(
                        IndestructibleAbility.getInstance(),
                        Duration.EndOfTurn
                ), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ResoluteWatchdog(final ResoluteWatchdog card) {
        super(card);
    }

    @Override
    public ResoluteWatchdog copy() {
        return new ResoluteWatchdog(this);
    }
}
