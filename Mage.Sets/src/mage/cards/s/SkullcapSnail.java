package mage.cards.s;

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
 * @author Susucr
 */
public final class SkullcapSnail extends CardImpl {

    public SkullcapSnail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.SNAIL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Skullcap Snail enters the battlefield, target opponent exiles a card from their hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileFromZoneTargetEffect(Zone.HAND, false));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SkullcapSnail(final SkullcapSnail card) {
        super(card);
    }

    @Override
    public SkullcapSnail copy() {
        return new SkullcapSnail(this);
    }
}
