
package mage.cards.e;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 * @author LevelX - changed to checkInterveningIfClause
 */
public final class EmeriaTheSkyRuin extends CardImpl {

    public EmeriaTheSkyRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Emeria, the Sky Ruin enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // At the beginning of your upkeep, if you control seven or more Plains, you may return target creature card from your graveyard to the battlefield.
        this.addAbility(new EmeriaTheSkyRuinTriggeredAbility()
                .addHint(new ValueHint("Plains you control", new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.PLAINS)))));
        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private EmeriaTheSkyRuin(final EmeriaTheSkyRuin card) {
        super(card);
    }

    @Override
    public EmeriaTheSkyRuin copy() {
        return new EmeriaTheSkyRuin(this);
    }
}

class EmeriaTheSkyRuinTriggeredAbility extends TriggeredAbilityImpl {

    static final FilterLandPermanent filter = new FilterLandPermanent("Plains");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    public EmeriaTheSkyRuinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        this.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private EmeriaTheSkyRuinTriggeredAbility(final EmeriaTheSkyRuinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EmeriaTheSkyRuinTriggeredAbility copy() {
        return new EmeriaTheSkyRuinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().countAll(filter, this.controllerId, game) >= 7;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you control seven or more Plains, you may return target creature card from your graveyard to the battlefield.";
    }
}
