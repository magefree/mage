package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.CastFromGraveyardOnceEachTurnAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.card.MilledThisTurnPredicate;
import mage.watchers.common.CardsMilledWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RaulTroubleShooter extends CardImpl {

    private static final FilterCard filter = new FilterCard("a spell from among cards in your graveyard that were milled this turn");

    static {
        filter.add(MilledThisTurnPredicate.instance);
    }

    public RaulTroubleShooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Once during each of your turns, you may cast a spell from among cards in your graveyard that were milled this turn.
        this.addAbility(new CastFromGraveyardOnceEachTurnAbility(filter), new CardsMilledWatcher());

        // {T}: Each player mills a card.
        this.addAbility(new SimpleActivatedAbility(
                new MillCardsEachPlayerEffect(1, TargetController.ANY),
                new TapSourceCost()
        ));
    }

    private RaulTroubleShooter(final RaulTroubleShooter card) {
        super(card);
    }

    @Override
    public RaulTroubleShooter copy() {
        return new RaulTroubleShooter(this);
    }
}