package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class MinisterOfPain extends CardImpl {

    public MinisterOfPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Exploit
        this.addAbility(new ExploitAbility());
        
        // When Minister of Pain explots a creature, creatures your opponents control get -1/-1 until end of turn.
        this.addAbility(new ExploitCreatureTriggeredAbility(
                new BoostAllEffect(-1, -1, Duration.EndOfTurn, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false)));
        
    }

    private MinisterOfPain(final MinisterOfPain card) {
        super(card);
    }

    @Override
    public MinisterOfPain copy() {
        return new MinisterOfPain(this);
    }
}
