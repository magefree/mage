
package mage.cards.c;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.Watcher;

/**
 *
 * @author L_J
 */
public final class CustodiSoulcaller extends CardImpl {

    public CustodiSoulcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Melee
        this.addAbility(new MeleeAbility());

        // Whenever Custodi Soulcaller attacks, return target creature card with converted mana cost X or less from your graveyard to the battlefield, where X is the number of players you attacked with a creature this combat.
        Ability ability = new AttacksTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false);
        ability.addWatcher(new CustodiSoulcallerWatcher());
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card with converted mana cost X or less from your graveyard, where X is the number of players you attacked with a creature this combat")));
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getClass().equals(AttacksTriggeredAbility.class)) {
            ability.getTargets().clear();
            CustodiSoulcallerWatcher watcher = (CustodiSoulcallerWatcher) game.getState().getWatchers().get(CustodiSoulcallerWatcher.class.getSimpleName());
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(ability.getSourceId());
            if (watcher != null && watcher.playersAttacked != null) {
                int xValue = watcher.getNumberOfAttackedPlayers(sourcePermanent.getControllerId());
                FilterCard filter = new FilterCard("creature card with converted mana cost " + xValue + " or less");
                filter.add(new CardTypePredicate(CardType.CREATURE));
                filter.add(Predicates.or(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue), new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, xValue)));
                ability.getTargets().add(new TargetCardInYourGraveyard(filter));
            }
        }
    }

    public CustodiSoulcaller(final CustodiSoulcaller card) {
        super(card);
    }

    @Override
    public CustodiSoulcaller copy() {
        return new CustodiSoulcaller(this);
    }
}

class CustodiSoulcallerWatcher extends Watcher {

    protected final HashMap<UUID, Set<UUID>> playersAttacked = new HashMap<>(0);

    CustodiSoulcallerWatcher() {
        super("CustodiSoulcallerWatcher", WatcherScope.GAME);
    }

    CustodiSoulcallerWatcher(final CustodiSoulcallerWatcher watcher) {
        super(watcher);
        this.playersAttacked.putAll(watcher.playersAttacked);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.BEGIN_COMBAT_STEP_PRE) {
            this.playersAttacked.clear();
        }
        else if (event.getType() == EventType.ATTACKER_DECLARED) {
            Set<UUID> attackedPlayers = this.playersAttacked.getOrDefault(event.getPlayerId(), new HashSet<>(1));
            attackedPlayers.add(event.getTargetId());
            this.playersAttacked.put(event.getPlayerId(), attackedPlayers);
        }
    }

    public int getNumberOfAttackedPlayers(UUID attackerId) {
        return this.playersAttacked.get(attackerId).size();
    }

    @Override
    public CustodiSoulcallerWatcher copy() {
        return new CustodiSoulcallerWatcher(this);
    }
}
