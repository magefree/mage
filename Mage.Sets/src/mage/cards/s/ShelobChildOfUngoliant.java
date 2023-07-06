package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.*;

/**
 *
 * @author Susucr
 */
public final class ShelobChildOfUngoliant extends CardImpl {

    private static final FilterControlledPermanent filterSpiders =
        new FilterControlledPermanent(SubType.SPIDER, "other Spiders");

    static {
        filterSpiders.add(AnotherPredicate.instance);
    }

    public ShelobChildOfUngoliant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Other Spiders you control have deathtouch and ward {2}.
        Ability buff = new SimpleStaticAbility(new GainAbilityControlledEffect(
            DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield,
            filterSpiders
        ));
        buff.addEffect(new GainAbilityControlledEffect(
            new WardAbility(new ManaCostsImpl<>("{2}")), Duration.WhileOnBattlefield,
            filterSpiders
        ).setText(" and ward {2}"));
        this.addAbility(buff);

        // Whenever another creature dealt damage this turn by a Spider you controlled dies,
        // create a token that's a copy of that creature, except it's a Food artifact with
        // "{2}, {T}, Sacrifice this artifact: You gain 3 life," and it loses all other card types.
        this.addAbility(
            new ShelobChildOfUngoliantTriggeredAbility(
                new ShelobChildOfUngoliantEffect()
            )
        );
    }

    private ShelobChildOfUngoliant(final ShelobChildOfUngoliant card) {
        super(card);
    }

    @Override
    public ShelobChildOfUngoliant copy() {
        return new ShelobChildOfUngoliant(this);
    }
}

class ShelobChildOfUngoliantWatcher extends Watcher {
    private static final FilterControlledPermanent spiderFilter =
        new FilterControlledPermanent(SubType.SPIDER,"spiders you controlled");

    // We store every permanent, as a non-creature may be dealt damage,
    // then become a creature then die.
    // map players to damaged permanents by a spider under that player's control.
    private final Map<UUID, Set<MageObjectReference>> damagedPermanents = new HashMap<>();

    public ShelobChildOfUngoliantWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PERMANENT && !game.isSimulation()) {
            Permanent damagedPermanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            Permanent spider = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (damagedPermanent == null || spider == null) {
                return;
            }
            if(!spiderFilter.match(spider, game)){
                return;
            }

            UUID playerUUID = spider.getControllerId();
            Set<MageObjectReference> setForThatPlayer = damagedPermanents.getOrDefault(spider.getControllerId(), new HashSet<>());
            // Not sure this test is necessary, as the spiderFilter is a FilterControlledPermanent
            if (controllerId != null && controllerId.equals(game.getControllerId(event.getSourceId()))) {
                setForThatPlayer.add(new MageObjectReference(event.getTargetId(), game));
                damagedPermanents.put(playerUUID, setForThatPlayer);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagedPermanents.clear();
    }

    public boolean wasDamaged(UUID playerUUID, Permanent permanent, Game game) {
        if(!damagedPermanents.containsKey(playerUUID)){
            return false;
        }

        return damagedPermanents.get(playerUUID)
            .contains(new MageObjectReference(permanent, game));
    }
}

class ShelobChildOfUngoliantTriggeredAbility extends TriggeredAbilityImpl {

    public ShelobChildOfUngoliantTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        this.addWatcher(new ShelobChildOfUngoliantWatcher());
        this.setTriggerPhrase("Whenever another creature dealt damage this turn by a Spider you controlled dies, ");
    }

    public ShelobChildOfUngoliantTriggeredAbility(final ShelobChildOfUngoliantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShelobChildOfUngoliantTriggeredAbility copy() {
        return new ShelobChildOfUngoliantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).isDiesEvent()) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            Permanent dyingPermanent = zEvent.getTarget();
            if (StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE.match(dyingPermanent, game)) {
                ShelobChildOfUngoliantWatcher watcher = game.getState().getWatcher(ShelobChildOfUngoliantWatcher.class);
                if(watcher == null){
                    return false;
                }

                if(!watcher.wasDamaged(this.controllerId, dyingPermanent, game)){
                    return false;
                }

                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
                }

                return true;
            }
        }
        return false;
    }
}

class ShelobChildOfUngoliantEffect extends OneShotEffect {

    public ShelobChildOfUngoliantEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of that creature, except it's a Food artifact with " +
            "\"{2}, {T}, Sacrifice this artifact: You gain 3 life,\" and it loses all other card types.";
    }

    public ShelobChildOfUngoliantEffect(final ShelobChildOfUngoliantEffect effect) {
        super(effect);
    }

    @Override
    public ShelobChildOfUngoliantEffect copy() {
        return new ShelobChildOfUngoliantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent copyFrom = targetPointer.getFirstTargetPermanentOrLKI(game, source);
        if(controller == null || copyFrom == null) {
            return false;
        }

        return new CreateTokenCopyTargetEffect().setSavedPermanent(copyFrom)
            .setPermanentModifier((token) -> {
                token.removeAllCardTypes();
                // We keep artifact subtypes, clearing the rest.
                token.getSubtype().retainAll(SubType.getArtifactTypes());
                token.addCardType(CardType.ARTIFACT);
                token.addSubType(SubType.FOOD);

                // {2}, {T}, Sacrifice this artifact: You gain 3 life.
                Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(3), new GenericManaCost(2));
                ability.addCost(new TapSourceCost());
                SacrificeSourceCost cost = new SacrificeSourceCost();
                cost.setText("Sacrifice this artifact");
                ability.addCost(cost);
                token.addAbility(ability);
            }).apply(game, source);
    }
}
