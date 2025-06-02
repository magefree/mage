package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.CastFromGraveyardOnceDuringEachOfYourTurnAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author balazskristof
 */
public final class BanonTheReturnersLeader extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard(
            "a creature spell from among cards in your graveyard that were put there from anywhere other than the battlefield this turn"
    );

    static {
        filter.add(BanonTheReturnersLeaderPredicate.instance);
    }

    public BanonTheReturnersLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Pray -- Once during each of your turns, you may cast a creature spell from among cards in your graveyard that were put there from anywhere other than the battlefield this turn.
        Ability ability = new CastFromGraveyardOnceDuringEachOfYourTurnAbility(filter).withFlavorWord("Pray");
        ability.addWatcher(new CardsPutIntoGraveyardWatcher());
        this.addAbility(ability);
        // Whenever you attack, you may pay {1} and discard a card. If you do, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DoIfCostPaid(
                        new DrawCardSourceControllerEffect(1),
                        new CompositeCost(new ManaCostsImpl<>("{1}"), new DiscardCardCost(), "pay {1} and discard a card")
                ), 1
        ));
    }

    private BanonTheReturnersLeader(final BanonTheReturnersLeader card) {
        super(card);
    }

    @Override
    public BanonTheReturnersLeader copy() {
        return new BanonTheReturnersLeader(this);
    }
}

enum BanonTheReturnersLeaderPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        CardsPutIntoGraveyardWatcher watcher = game.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);
        return watcher != null && watcher.checkCardNotFromBattlefield(input, game);
    }
}
