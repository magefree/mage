package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandFromGraveyardAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Gleancrawler extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard();

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    public Gleancrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B/G}{B/G}{B/G}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // <i>({B/G} can be paid with either {B} or {G}.)</i>

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your end step, return to your hand all creature cards in your graveyard that were put there from the battlefield this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ReturnToHandFromGraveyardAllEffect(filter, TargetController.YOU)
                        .setText("return to your hand all creature cards in your graveyard " +
                                "that were put there from the battlefield this turn"),
                TargetController.YOU, false
        ), new CardsPutIntoGraveyardWatcher());
    }

    private Gleancrawler(final Gleancrawler card) {
        super(card);
    }

    @Override
    public Gleancrawler copy() {
        return new Gleancrawler(this);
    }
}
