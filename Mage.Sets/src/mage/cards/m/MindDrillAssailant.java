package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MindDrillAssailant extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(7);

    public MindDrillAssailant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/B}{U/B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Threshold -- As long as seven or more cards are in your graveyard, Mind Drill Assailant gets +3/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield),
                condition, "as long as seven or more cards are in your graveyard, {this} gets +3/+0"
        )).setAbilityWord(AbilityWord.THRESHOLD));

        // {2}{U/B}: Surveil 1.
        this.addAbility(new SimpleActivatedAbility(new SurveilEffect(1), new ManaCostsImpl<>("{2}{U/B}")));
    }

    private MindDrillAssailant(final MindDrillAssailant card) {
        super(card);
    }

    @Override
    public MindDrillAssailant copy() {
        return new MindDrillAssailant(this);
    }
}
