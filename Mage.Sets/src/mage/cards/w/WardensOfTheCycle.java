package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class WardensOfTheCycle extends CardImpl {

    public WardensOfTheCycle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Morbid -- At the beginning of your end step, if a creature died this turn, choose one --
        // * You gain 2 life.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new GainLifeEffect(2))
                .withInterveningIf(MorbidCondition.instance).setAbilityWord(AbilityWord.MORBID).addHint(MorbidHint.instance);

        // * You draw a card and you lose 1 life.
        Mode mode = new Mode(new DrawCardSourceControllerEffect(1));
        mode.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private WardensOfTheCycle(final WardensOfTheCycle card) {
        super(card);
    }

    @Override
    public WardensOfTheCycle copy() {
        return new WardensOfTheCycle(this);
    }
}
