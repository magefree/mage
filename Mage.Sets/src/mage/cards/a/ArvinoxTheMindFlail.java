package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801 plus everyone who worked on Gonti
 */
public final class ArvinoxTheMindFlail extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Permanents you control but don't own", xValue);

    public ArvinoxTheMindFlail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Mind Flayer, the Shadow isn't a creature unless you control three or more permanents you don't own.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(xValue, 3)
                .setText("{this} isn't a creature unless you control three or more permanents you don't own")
        ).addHint(hint));

        // At the beginning of your end step, exile the bottom card of each opponent's library face down. For as long as those cards remain exiled, you may look at them, you may cast permanent spells from among them, and you may spend mana as though it were mana of any color to cast those spells.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ArvinoxTheMindFlailExileEffect()
        ));
    }

    private ArvinoxTheMindFlail(final ArvinoxTheMindFlail card) {
        super(card);
    }

    @Override
    public ArvinoxTheMindFlail copy() {
        return new ArvinoxTheMindFlail(this);
    }
}

class ArvinoxTheMindFlailExileEffect extends OneShotEffect {

    ArvinoxTheMindFlailExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the bottom card of each opponent's library face down. "
                + "For as long as those cards remain exiled, you may look at them, "
                + "you may cast permanent spells from among them, "
                + "and you may spend mana as though it were mana of any color to cast those spells";
    }

    private ArvinoxTheMindFlailExileEffect(final ArvinoxTheMindFlailExileEffect effect) {
        super(effect);
    }

    @Override
    public ArvinoxTheMindFlailExileEffect copy() {
        return new ArvinoxTheMindFlailExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            cards.add(opponent.getLibrary().getFromBottom(game));
        }
        if (CardUtil.moveCardsToExileFaceDown(game, source, controller, cards.getCards(game), true)) {
            for (Card card : cards.getCards(game)) {
                if (card.isPermanent(game)) {
                    CardUtil.makeCardPlayable(game, source, card, true, Duration.Custom, true);
                }
            }
        }
        return true;
    }
}