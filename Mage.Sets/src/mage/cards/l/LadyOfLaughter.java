package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.watchers.common.CelebrationWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LadyOfLaughter extends CardImpl {

    public LadyOfLaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Celebration -- At the beginning of your end step, if two or more nonland permanents entered the battlefield under your control this turn, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DrawCardSourceControllerEffect(1), TargetController.YOU,
                CelebrationCondition.instance, false
        ).addHint(CelebrationCondition.getHint()).setAbilityWord(AbilityWord.CELEBRATION), new CelebrationWatcher());
    }

    private LadyOfLaughter(final LadyOfLaughter card) {
        super(card);
    }

    @Override
    public LadyOfLaughter copy() {
        return new LadyOfLaughter(this);
    }
}
