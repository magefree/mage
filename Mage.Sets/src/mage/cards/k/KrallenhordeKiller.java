package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Loki
 */
public final class KrallenhordeKiller extends CardImpl {

    public KrallenhordeKiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setGreen(true);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.nightCard = true;

        // {3}{G}: Krallenhorde Killer gets +4/+4 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(4, 4, Duration.EndOfTurn), new ManaCostsImpl<>("{3}{G}")));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Krallenhorde Killer.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private KrallenhordeKiller(final KrallenhordeKiller card) {
        super(card);
    }

    @Override
    public KrallenhordeKiller copy() {
        return new KrallenhordeKiller(this);
    }
}
