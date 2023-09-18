package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoaringThoughtThief extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ROGUE, "Rogues");

    public SoaringThoughtThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As long as an opponent has eight or more cards in their graveyard, Rogues you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filter),
                CardsInOpponentGraveyardCondition.EIGHT, "as long as an opponent has eight or more cards " +
                "in their graveyard, Rogues you control get +1/+0"
        )).addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint()));

        // Whenever one or more Rogues you control attack, each opponent mills two cards.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new MillCardsEachPlayerEffect(2, TargetController.OPPONENT), 1, filter
        ).setTriggerPhrase("Whenever one or more Rogues you control attack, "));
    }

    private SoaringThoughtThief(final SoaringThoughtThief card) {
        super(card);
    }

    @Override
    public SoaringThoughtThief copy() {
        return new SoaringThoughtThief(this);
    }
}
