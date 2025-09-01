package mage.cards.w;

import mage.MageInt;
import mage.abilities.condition.common.LandfallCondition;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.hint.common.CurrentDungeonHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.LandfallWatcher;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class WanderingTroubadour extends CardImpl {

    public WanderingTroubadour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, if you had a land enter the battlefield under your control this turn, venture into the dungeon.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new VentureIntoTheDungeonEffect())
                .withInterveningIf(LandfallCondition.instance).addHint(CurrentDungeonHint.instance), new LandfallWatcher());
    }

    private WanderingTroubadour(final WanderingTroubadour card) {
        super(card);
    }

    @Override
    public WanderingTroubadour copy() {
        return new WanderingTroubadour(this);
    }
}
