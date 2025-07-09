package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.EnterUntappedAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.LanderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HorizonExplorer extends CardImpl {

    public HorizonExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Lands you control enter untapped.
        this.addAbility(new SimpleStaticAbility(new EnterUntappedAllEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS)));

        // Whenever you attack a player, create a Lander token.
        this.addAbility(new AttacksPlayerWithCreaturesTriggeredAbility(
                new CreateTokenEffect(new LanderToken()),
                StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED, SetTargetPointer.NONE
        ));
    }

    private HorizonExplorer(final HorizonExplorer card) {
        super(card);
    }

    @Override
    public HorizonExplorer copy() {
        return new HorizonExplorer(this);
    }
}
