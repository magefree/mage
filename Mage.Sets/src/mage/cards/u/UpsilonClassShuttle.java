package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class UpsilonClassShuttle extends CardImpl {

    public UpsilonClassShuttle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // Whenever Upsilon-class Shuttle attacks, target creature you control gains spaceflight until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new GainAbilityTargetEffect(SpaceflightAbility.getInstance(), Duration.EndOfTurn)
                    .setText("target creature you control gains spaceflight until end of turn"),
                false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    public UpsilonClassShuttle(final UpsilonClassShuttle card) {
        super(card);
    }

    @Override
    public UpsilonClassShuttle copy() {
        return new UpsilonClassShuttle(this);
    }
}
