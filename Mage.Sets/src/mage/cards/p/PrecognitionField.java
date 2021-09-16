package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author rscoates
 */
public final class PrecognitionField extends CardImpl {

    private static final FilterCard filter = new FilterCard("cast instant or sorcery spells");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public PrecognitionField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // You may look at the top card of your library.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast instant and sorcery spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect(TargetController.YOU, filter, false)));

        // {3}: Exile the top card of your library.
        this.addAbility(new SimpleActivatedAbility(new PrecognitionFieldExileEffect(), new GenericManaCost(3)));
    }

    private PrecognitionField(final PrecognitionField card) {
        super(card);
    }

    @Override
    public PrecognitionField copy() {
        return new PrecognitionField(this);
    }
}

class PrecognitionFieldExileEffect extends OneShotEffect {

    public PrecognitionFieldExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library";
    }

    public PrecognitionFieldExileEffect(final PrecognitionFieldExileEffect effect) {
        super(effect);
    }

    @Override
    public PrecognitionFieldExileEffect copy() {
        return new PrecognitionFieldExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.moveCards(card, Zone.EXILED, source, game);
            }
            return true;
        }
        return false;
    }
}
