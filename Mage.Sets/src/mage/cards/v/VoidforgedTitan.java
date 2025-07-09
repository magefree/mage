package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.VoidCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.VoidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoidforgedTitan extends CardImpl {

    public VoidforgedTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Void -- At the beginning of your end step, if a nonland permanent left the battlefield this turn or a spell was warped this turn, you draw a card and lose 1 life.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new DrawCardSourceControllerEffect(1, true)
        ).withInterveningIf(VoidCondition.instance);
        ability.addEffect(new LoseLifeSourceControllerEffect(1).setText("and lose 1 life"));
        this.addAbility(ability.setAbilityWord(AbilityWord.VOID).addHint(VoidCondition.getHint()), new VoidWatcher());
    }

    private VoidforgedTitan(final VoidforgedTitan card) {
        super(card);
    }

    @Override
    public VoidforgedTitan copy() {
        return new VoidforgedTitan(this);
    }
}
