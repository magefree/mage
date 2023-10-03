package mage.cards.c;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Contamination extends CardImpl {

    public Contamination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // At the beginning of your upkeep, sacrifice Contamination unless you sacrifice a creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)), TargetController.YOU, false)
        );

        // If a land is tapped for mana, it produces {B} instead of any other type and amount.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ContaminationReplacementEffect()));
    }

    private Contamination(final Contamination card) {
        super(card);
    }

    @Override
    public Contamination copy() {
        return new Contamination(this);
    }
}

class ContaminationReplacementEffect extends ReplacementEffectImpl {

    ContaminationReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If a land is tapped for mana, it produces {B} instead of any other type and amount";
    }

    private ContaminationReplacementEffect(final ContaminationReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ContaminationReplacementEffect copy() {
        return new ContaminationReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ManaEvent manaEvent = (ManaEvent) event;
        Mana mana = manaEvent.getMana();
        mana.setToMana(Mana.BlackMana(1));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        return permanent != null && permanent.isLand(game);
    }
}
