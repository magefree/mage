package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class InventorsFair extends CardImpl {

    public InventorsFair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        addSuperType(SuperType.LEGENDARY);

        // At the beginning of your upkeep, if you control three or more artifacts, you gain 1 life.
        this.addAbility(new InventorsFairAbility());

        // {t}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}, {T}, Sacrifice Inventors' Fair: Search your library for an artifact card, reveal it,
        //                                      put it into your hand, then shuffle your library.
        //                                      Activate this ability only if you control three or more artifacts.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_ARTIFACT), true),
                new GenericManaCost(4), MetalcraftCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addHint(MetalcraftHint.instance);
        this.addAbility(ability);
    }

    private InventorsFair(final InventorsFair card) {
        super(card);
    }

    @Override
    public InventorsFair copy() {
        return new InventorsFair(this);
    }
}

class InventorsFairAbility extends TriggeredAbilityImpl {

    public InventorsFairAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1));
    }

    public InventorsFairAbility(final InventorsFairAbility ability) {
        super(ability);
    }

    @Override
    public InventorsFairAbility copy() {
        return new InventorsFairAbility(this);
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
        return game.getBattlefield().countAll(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, this.controllerId, game) >= 3;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you control three or more artifacts, you gain 1 life.";
    }
}