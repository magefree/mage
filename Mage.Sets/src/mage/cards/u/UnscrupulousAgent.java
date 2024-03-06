package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class UnscrupulousAgent extends CardImpl {

    public UnscrupulousAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Unscrupulous Agent enters the battlefield, target opponent exiles a card from their hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileFromZoneTargetEffect(Zone.HAND, false));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    private UnscrupulousAgent(final UnscrupulousAgent card) {
        super(card);
    }

    @Override
    public UnscrupulousAgent copy() {
        return new UnscrupulousAgent(this);
    }
}
