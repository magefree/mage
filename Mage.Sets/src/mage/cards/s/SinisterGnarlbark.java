package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.BlightControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SinisterGnarlbark extends CardImpl {

    public SinisterGnarlbark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, draw a card and blight 1.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new BlightControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private SinisterGnarlbark(final SinisterGnarlbark card) {
        super(card);
    }

    @Override
    public SinisterGnarlbark copy() {
        return new SinisterGnarlbark(this);
    }
}
