
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.HauntAbility;
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
public final class OrzhovPontiff extends CardImpl {
    public OrzhovPontiff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haunt
        // When Orzhov Pontiff enters the battlefield or the creature it haunts dies, choose one - Creatures you control get +1/+1 until end of turn; or creatures you don't control get -1/-1 until end of turn.
        Ability ability = new HauntAbility(this, new BoostAllEffect(1,1, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED, false));
        Mode mode = new Mode(new BoostAllEffect(-1,-1, Duration.EndOfTurn, StaticFilters.FILTER_CREATURES_YOU_DONT_CONTROL, false));
        ability.addMode(mode);
        this.addAbility(ability);

    }

    private OrzhovPontiff(final OrzhovPontiff card) {
        super(card);
    }

    @Override
    public OrzhovPontiff copy() {
        return new OrzhovPontiff(this);
    }
}
