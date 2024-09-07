
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.ManaValueTargetAdjuster;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author L_J
 */
public final class CustodiSoulcaller extends CardImpl {
    public CustodiSoulcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Melee
        this.addAbility(new MeleeAbility());

        // Whenever Custodi Soulcaller attacks, return target creature card with converted mana cost X or less from your graveyard to the battlefield, where X is the number of players you attacked with a creature this combat.
        Ability ability = new AttacksTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect().setText("return target creature card with mana value X or less from your graveyard to the battlefield, where X is the number of players you attacked this combat"), false);
        ability.addWatcher(new CustodiSoulcallerWatcher());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.setTargetAdjuster(new ManaValueTargetAdjuster(CustodiSoulcallerValue.instance, ComparisonType.OR_LESS));
        this.addAbility(ability);
    }

    private CustodiSoulcaller(final CustodiSoulcaller card) {
        super(card);
    }

    @Override
    public CustodiSoulcaller copy() {
        return new CustodiSoulcaller(this);
    }
}

enum CustodiSoulcallerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CustodiSoulcallerWatcher watcher = game.getState().getWatcher(CustodiSoulcallerWatcher.class);
        if (watcher != null) {
            return watcher.getNumberOfAttackedPlayers(sourceAbility.getControllerId());
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class CustodiSoulcallerWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> playersAttacked = new HashMap<>(0);

    CustodiSoulcallerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
            this.playersAttacked.clear();
        } else if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            Set<UUID> attackedPlayers = this.playersAttacked.getOrDefault(event.getPlayerId(), new HashSet<>(1));
            attackedPlayers.add(event.getTargetId());
            this.playersAttacked.put(event.getPlayerId(), attackedPlayers);
        }
    }

    public int getNumberOfAttackedPlayers(UUID attackerId) {
        return this.playersAttacked.get(attackerId).size();
    }
}
