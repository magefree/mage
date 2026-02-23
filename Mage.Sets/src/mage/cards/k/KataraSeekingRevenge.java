package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.LessonsInGraveCondition;
import mage.abilities.condition.common.WaterbendedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.WaterbendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KataraSeekingRevenge extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(new FilterCard(SubType.LESSON));

    public KataraSeekingRevenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As an additional cost to cast this spell, you may waterbend {2}.
        this.addAbility(new WaterbendAbility(2));

        // When Katara enters, draw a card, then discard a card unless her additional cost was paid.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new ConditionalOneShotEffect(
                null, new DiscardControllerEffect(1), WaterbendedCondition.instance,
                ", then discard a card unless her additional cost was paid"
        ));
        this.addAbility(ability);

        // Katara gets +1/+1 for each Lesson card in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)
        ).addHint(LessonsInGraveCondition.getHint()));
    }

    private KataraSeekingRevenge(final KataraSeekingRevenge card) {
        super(card);
    }

    @Override
    public KataraSeekingRevenge copy() {
        return new KataraSeekingRevenge(this);
    }
}
