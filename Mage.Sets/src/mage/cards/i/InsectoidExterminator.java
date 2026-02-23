package mage.cards.i;

import mage.MageInt;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InsectoidExterminator extends CardImpl {

    public InsectoidExterminator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Disappear -- At the beginning of your end step, if a permanent left the battlefield under your control this turn, scry 1.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ScryEffect(1))
                .withInterveningIf(RevoltCondition.instance)
                .addHint(RevoltCondition.getHint())
                .setAbilityWord(AbilityWord.DISAPPEAR), new RevoltWatcher());
    }

    private InsectoidExterminator(final InsectoidExterminator card) {
        super(card);
    }

    @Override
    public InsectoidExterminator copy() {
        return new InsectoidExterminator(this);
    }
}
