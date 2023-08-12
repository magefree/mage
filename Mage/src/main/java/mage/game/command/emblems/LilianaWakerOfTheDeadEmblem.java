package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.ReturnCreatureFromGraveyardToBattlefieldAndGainHasteEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;
import mage.target.common.TargetCardInGraveyard;

public final class LilianaWakerOfTheDeadEmblem extends Emblem {
    /**
     * Emblem with "At the beginning of combat on your turn, put target creature card from a graveyard onto the battlefield under your control. It gains haste."
     */

    public LilianaWakerOfTheDeadEmblem() {
        super("Emblem Liliana");
        Ability ability = new BeginningOfCombatTriggeredAbility(
                Zone.COMMAND,
                new ReturnCreatureFromGraveyardToBattlefieldAndGainHasteEffect(),
                TargetController.YOU, false, false);
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.getAbilities().add(ability);
    }

    private LilianaWakerOfTheDeadEmblem(final LilianaWakerOfTheDeadEmblem card) {
        super(card);
    }

    @Override
    public LilianaWakerOfTheDeadEmblem copy() {
        return new LilianaWakerOfTheDeadEmblem(this);
    }
}
