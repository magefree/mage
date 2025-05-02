package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.PutIntoGraveFromAnywhereThisTurnPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class AbyssalHarvester extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card from a graveyard that was put there this turn"
    );

    static {
        filter.add(PutIntoGraveFromAnywhereThisTurnPredicate.instance);
    }

    public AbyssalHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {T}: Exile target creature card from a graveyard that was put there this turn. Create a token that's a copy of it, except it's a Nightmare in addition to its other types. Then exile all other Nightmare tokens you control.
        Ability ability = new SimpleActivatedAbility(new AbyssalHarvesterEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard(filter));
        ability.addWatcher(new CardsPutIntoGraveyardWatcher());
        this.addAbility(ability);
    }

    private AbyssalHarvester(final AbyssalHarvester card) {
        super(card);
    }

    @Override
    public AbyssalHarvester copy() {
        return new AbyssalHarvester(this);
    }
}

class AbyssalHarvesterEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent();
    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(SubType.NIGHTMARE.getPredicate());
    }

    AbyssalHarvesterEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Exile target creature card from a graveyard that was put there this turn. "
                + "Create a token that's a copy of it, except it's a Nightmare in addition to its other types. "
                + "Then exile all other Nightmare tokens you control";
    }

    private AbyssalHarvesterEffect(final AbyssalHarvesterEffect effect) {
        super(effect);
    }

    @Override
    public AbyssalHarvesterEffect copy() {
        return new AbyssalHarvesterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game); // Also if the move to exile is replaced, the copy takes place
        game.processAction();
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, false, 1, false, false, null);
        effect.setTargetPointer(new FixedTarget(card, game));
        effect.withAdditionalSubType(SubType.NIGHTMARE);
        effect.apply(game, source);
        game.processAction();
        List<Permanent> addedTokens = effect.getAddedPermanents();
        Cards cards = new CardsImpl();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (!addedTokens.contains(permanent)) {
                cards.add(permanent.getId());
            }
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        return true;
    }
}
