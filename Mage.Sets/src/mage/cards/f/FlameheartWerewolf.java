package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FlameheartWerewolf extends CardImpl {

    public FlameheartWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Whenever Flameheart Werewolf blocks or becomes blocked by a creature, Flameheart Werewolf deals 2 damage to that creature.
        this.addAbility(new BlocksOrBecomesBlockedSourceTriggeredAbility(new DamageTargetEffect(2, true, "that creature"),
                StaticFilters.FILTER_PERMANENT_CREATURE, false, null, true));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Flameheart Werewolf.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private FlameheartWerewolf(final FlameheartWerewolf card) {
        super(card);
    }

    @Override
    public FlameheartWerewolf copy() {
        return new FlameheartWerewolf(this);
    }
}
