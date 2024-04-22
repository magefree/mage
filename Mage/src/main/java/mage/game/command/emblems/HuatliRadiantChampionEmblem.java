package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author LevelX2
 */
public final class HuatliRadiantChampionEmblem extends Emblem {

    public HuatliRadiantChampionEmblem() {
        super("Emblem Huatli");

        // Whenever a creature enters the battlefield under your control, you may draw a card.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.COMMAND,
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_CONTROLLED_A_CREATURE, true);
        this.getAbilities().add(ability);
    }

    private HuatliRadiantChampionEmblem(final HuatliRadiantChampionEmblem card) {
        super(card);
    }

    @Override
    public HuatliRadiantChampionEmblem copy() {
        return new HuatliRadiantChampionEmblem(this);
    }
}
