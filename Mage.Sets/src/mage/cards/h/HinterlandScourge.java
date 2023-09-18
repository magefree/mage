package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class HinterlandScourge extends CardImpl {

    public HinterlandScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setRed(true);

        // this card is the second face of double-faced card of Hinterland Hermit
        this.nightCard = true;

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Hinterland Scourge must be blocked if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Hinterland Scourge.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private HinterlandScourge(final HinterlandScourge card) {
        super(card);
    }

    @Override
    public HinterlandScourge copy() {
        return new HinterlandScourge(this);
    }
}
