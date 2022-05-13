
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.abilities.keyword.ChampionAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class MistbindClique extends CardImpl {

    public MistbindClique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Champion a Faerie
        this.addAbility(new ChampionAbility(this, SubType.FAERIE));
        // When a Faerie is championed with Mistbind Clique, tap all lands target player controls.
        this.addAbility(new MistbindCliqueAbility());

    }

    private MistbindClique(final MistbindClique card) {
        super(card);
    }

    @Override
    public MistbindClique copy() {
        return new MistbindClique(this);
    }
}

class MistbindCliqueAbility extends ZoneChangeTriggeredAbility {

    public MistbindCliqueAbility() {
        // ability has to trigger independant where the source object is now
        super(Zone.ALL, Zone.BATTLEFIELD, Zone.EXILED, new TapAllTargetPlayerControlsEffect(StaticFilters.FILTER_LANDS), "When a Faerie is championed with {this}, ", false);
        this.addTarget(new TargetPlayer());
    }

    public MistbindCliqueAbility(MistbindCliqueAbility ability) {
        super(ability);
    }

    @Override
    public MistbindCliqueAbility copy() {
        return new MistbindCliqueAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_CHAMPIONED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId() != null
                && event.getSourceId().equals(getSourceId())
                && !event.getSourceId().equals(event.getTargetId())) {
            Permanent sacrificed = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (sacrificed != null) {// no longer checks for Faerie as LKI isn't always accurate, can't think of how that could matter anyway - TheElk801
                return true;
            }
        }
        return false;
    }
}
