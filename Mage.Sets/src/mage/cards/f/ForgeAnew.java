package mage.cards.f;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.ActivateAbilitiesAnyTimeYouCouldCastInstantEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.Watcher;

/**
 *
 * @author xanderhall
 */
public class ForgeAnew extends CardImpl {

    private static final FilterCard filter = new FilterCard("Equipment card from your graveyard");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public ForgeAnew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Forge Anew enters the battlefield, return target Equipment card from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        //As long as it’s your turn, you may activate equip abilities any time you could cast an instant.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
            new ActivateAbilitiesAnyTimeYouCouldCastInstantEffect(EquipAbility.class, "equip abilities"), MyTurnCondition.instance
            ).setText("as long as it's your turn, you may activate equip abilities any time you could cast an instant."))
        );

        //You may pay {0} rather than pay the equip cost of the first equip ability you activate during each of your turns.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new EquipAbility(0, false), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_EQUIPMENT
                ).setText("you may pay {0} rather than pay the equip cost of the first equip ability you activate during each of your turns.")
            ), new ForgeAnewWatcher()
        );
    }
    private ForgeAnew(final ForgeAnew card) {
        super(card);
    }

    @Override
    public ForgeAnew copy() {
        return new ForgeAnew(this);
    }
}

class ForgeAnewWatcher extends Watcher {

    private final Set<UUID> equippedThisTurn = new HashSet<>();

    ForgeAnewWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ACTIVATED_ABILITY) {
            return;
        }

        StackObject object = game.getStack().getStackObject(event.getSourceId());

        if (object != null && object.getStackAbility() instanceof EquipAbility) {
            equippedThisTurn.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        equippedThisTurn.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        ForgeAnewWatcher watcher = game.getState().getWatcher(ForgeAnewWatcher.class);
        return watcher != null && watcher.equippedThisTurn.contains(playerId);
    }
}