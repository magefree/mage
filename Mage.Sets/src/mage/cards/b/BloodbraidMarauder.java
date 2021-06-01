package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.CascadeAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author weirddan455
 */
public final class BloodbraidMarauder extends CardImpl {

    public BloodbraidMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Bloodbraid Marauder can't block.
        this.addAbility(new CantBlockAbility());

        // Delirium — This spell has cascade as long as there are four or more card types among cards in your graveyard.
        this.addAbility(new BloodbraidMarauderCascadeAbility());
    }

    private BloodbraidMarauder(final BloodbraidMarauder card) {
        super(card);
    }

    @Override
    public BloodbraidMarauder copy() {
        return new BloodbraidMarauder(this);
    }
}

class BloodbraidMarauderCascadeAbility extends CascadeAbility {

    public BloodbraidMarauderCascadeAbility() {
        super();
        addHint(CardTypesInGraveyardHint.YOU);
    }

    private BloodbraidMarauderCascadeAbility(final BloodbraidMarauderCascadeAbility ability) {
        super(ability);
    }

    @Override
    public BloodbraidMarauderCascadeAbility copy() {
        return new BloodbraidMarauderCascadeAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return super.checkTrigger(event, game) && DeliriumCondition.instance.apply(game, this);
    }

    @Override
    public String getRule() {
        return "<i>Delirium</i> &mdash; This spell has cascade as long as there are four or more card types among cards in your graveyard." + REMINDERTEXT;
    }
}
