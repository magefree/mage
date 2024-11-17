package mage.cards.e;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfAnyNumberCostPaid;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.FreerunningAbility;
import mage.constants.*;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

/**
 *
 * @author Kr4u7
 */
public final class EzioAuditoreDaFirenze extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("assassin spells you cast");

    static {
        filter.add(SubType.ASSASSIN.getPredicate());
        filter.add(Predicates.not(new AbilityPredicate(FreerunningAbility.class))); // So there are not redundant copies being added to each card
    }
    
    public EzioAuditoreDaFirenze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(), makeWatcher());

        // Assassin spells you cast have freerunning {B}{B}.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledSpellsEffect(new FreerunningAbility("{B}{B}"), filter)));
        // Whenever Ezio deals combat damage to a player, you may pay {W}{U}{B}{R}{G} if that player has 10 or less life. When you do, that player loses the game.
        //Phage this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new LoseGameTargetPlayerEffect(), false, true));

        //this.addAbility(new ConditionalTriggeredAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new EzioAuditoreDaFirenzeEffect(), true, true)), new LifeLossOtherFromCombatWatcher());
        //this.addAbility(new ConditionalTriggeredAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfAnyNumberCostPaid(new EzioAuditoreDaFirenzeEffect(),new ManaCostsImpl<>("{W}{U}{B}{R}{G}")), true, true),condition, "pay {W}{U}{B}{R}{G} if that player has 10 or less life. When you do, that player loses the game."));
        //Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(new EzioAuditoreDaFirenzeEffect(), new ManaCostsImpl<>("{W}{U}{B}{R}{G}")), true, true)
        this.addAbility(
                new ConditionalTriggeredAbility(
                        new DealsCombatDamageToAPlayerTriggeredAbility(
                                new DoIfCostPaid(
                                        new LoseGameTargetPlayerEffect(),new ManaCostsImpl<>("{W}{U}{B}{R}{G}")
                                ), false, true
                        ),EzioAuditoreDaFirenzeWatcher::checkPermanent, "Whenever {this} deals combat damage to a player, you may pay {W}{U}{B}{R}{G} if that player has 10 or less life. When you do, that player loses the game."
                )
        );

    }

    private EzioAuditoreDaFirenze(final EzioAuditoreDaFirenze card) {
        super(card);
    }

    @Override
    public EzioAuditoreDaFirenze copy() {
        return new EzioAuditoreDaFirenze(this);
    }

    public static Watcher makeWatcher(){
        return new EzioAuditoreDaFirenzeWatcher();
    }
}

class EzioAuditoreDaFirenzeWatcher extends Watcher {

    private final Map<Map.Entry<MageObjectReference, UUID>, Integer> morMap = new HashMap<>();

    EzioAuditoreDaFirenzeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null) {
            UUID target = event.getTargetId();
            Player player = game.getPlayer(target);
            int damage = event.getAmount();
            int life = player.getLife();
            morMap.compute(new AbstractMap.SimpleImmutableEntry(new MageObjectReference(permanent, game), event.getTargetId()),
                    (u, i) -> i == null ? life : Integer.sum(i, -damage));
        }
    }


    @Override
    public void reset() {
        super.reset();
        morMap.clear();
    }

    static boolean checkPermanent(Game game, Ability source) {
        Map<Map.Entry<MageObjectReference, UUID>, Integer> morMap = game.getState()
                .getWatcher(EzioAuditoreDaFirenzeWatcher.class)
                .morMap;
        Map.Entry<MageObjectReference, UUID> key = new AbstractMap.SimpleImmutableEntry(
                new MageObjectReference(game.getPermanent(source.getSourceId()), game),
                source.getEffects().get(0).getTargetPointer().getFirst(game, source));
        return morMap.getOrDefault(key, 0) <= 10;
    }
}