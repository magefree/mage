package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Styxo
 */
public final class NebulonBFrigate extends CardImpl {

    public NebulonBFrigate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // Whenever Nebulon-B Frigate enters the battlefield, creatures you control gain vigilance until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED), false));
    }

    private NebulonBFrigate(final NebulonBFrigate card) {
        super(card);
    }

    @Override
    public NebulonBFrigate copy() {
        return new NebulonBFrigate(this);
    }
}
