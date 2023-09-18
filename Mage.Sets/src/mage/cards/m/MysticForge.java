package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysticForge extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("cast artifact spells and colorless spells");

    static {
        filter.add(Predicates.or(
                ColorlessPredicate.instance,
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public MysticForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast artifact spells and colorless spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect(TargetController.YOU, filter, false)));

        // {T}, Pay 1 life: Exile the top card of your library.
        Ability ability = new SimpleActivatedAbility(new MysticForgeExileEffect(), new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private MysticForge(final MysticForge card) {
        super(card);
    }

    @Override
    public MysticForge copy() {
        return new MysticForge(this);
    }
}

class MysticForgeExileEffect extends OneShotEffect {

    MysticForgeExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library";
    }

    private MysticForgeExileEffect(final MysticForgeExileEffect effect) {
        super(effect);
    }

    @Override
    public MysticForgeExileEffect copy() {
        return new MysticForgeExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        return controller.moveCards(controller.getLibrary().getFromTop(game), Zone.EXILED, source, game);
    }
}
