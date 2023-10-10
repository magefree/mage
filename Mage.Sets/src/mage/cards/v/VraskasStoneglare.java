package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class VraskasStoneglare extends CardImpl {

    private static final FilterCard filter = new FilterCard("Vraska, Regal Gorgon");

    static {
        filter.add(new NamePredicate("Vraska, Regal Gorgon"));
    }

    public VraskasStoneglare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{G}");

        // Destroy target creature. You gain life equal to its toughness. You may search your library and/or graveyard from a card named Vraska, Regal Gordon, reveal it, and put it in to your hand. If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new VraskasStoneglareEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(
                new SearchLibraryGraveyardPutInHandEffect(filter, false, true)
        );
    }

    private VraskasStoneglare(final VraskasStoneglare card) {
        super(card);
    }

    @Override
    public VraskasStoneglare copy() {
        return new VraskasStoneglare(this);
    }
}

class VraskasStoneglareEffect extends OneShotEffect {

    public VraskasStoneglareEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy target creature. "
                + "You gain life equal to its toughness";
    }

    private VraskasStoneglareEffect(final VraskasStoneglareEffect effect) {
        super(effect);
    }

    @Override
    public VraskasStoneglareEffect copy() {
        return new VraskasStoneglareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        int toughness = permanent.getToughness().getValue();
        permanent.destroy(source, game, false);
        if (player != null) {
            player.gainLife(toughness, game, source);
        }
        return true;
    }
}
