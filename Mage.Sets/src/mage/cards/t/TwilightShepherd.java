package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandFromGraveyardAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PersistAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class TwilightShepherd extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    public TwilightShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Twilight Shepherd enters the battlefield, return to your hand all cards in your graveyard that were put there from the battlefield this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ReturnToHandFromGraveyardAllEffect(filter, TargetController.YOU)
                        .setText("return to your hand all cards in your graveyard " +
                                "that were put there from the battlefield this turn"),
                false
        ), new CardsPutIntoGraveyardWatcher());

        // Persist
        this.addAbility(new PersistAbility());
    }

    private TwilightShepherd(final TwilightShepherd card) {
        super(card);
    }

    @Override
    public TwilightShepherd copy() {
        return new TwilightShepherd(this);
    }
}
