package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.RevealPutInHandLoseLifeEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuinRaider extends CardImpl {

    public RuinRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Raid</i> &mdash; At the beginning of your end step, if you attacked this turn, reveal the top card of your library and put that card into your hand. You lose life equal to the card's converted mana cost.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new RevealPutInHandLoseLifeEffect(), TargetController.YOU, false
                ), RaidCondition.instance, "At the beginning of your end step, " +
                "if you attacked this turn, reveal the top card of your library " +
                "and put that card into your hand. You lose life equal to the card's mana value."
        );
        ability.setAbilityWord(AbilityWord.RAID);
        ability.addHint(RaidHint.instance);
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    private RuinRaider(final RuinRaider card) {
        super(card);
    }

    @Override
    public RuinRaider copy() {
        return new RuinRaider(this);
    }
}
