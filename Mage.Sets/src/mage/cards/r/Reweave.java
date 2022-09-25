package mage.cards.r;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class Reweave extends CardImpl {

    public Reweave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{U}");
        this.subtype.add(SubType.ARCANE);

        // Target permanent's controller sacrifices it. If they do, that player reveals cards from the top of their library until they reveal a permanent card that shares a card type with the sacrificed permanent, puts that card onto the battlefield, then shuffles their library.
        this.getSpellAbility().addEffect(new ReweaveEffect());
        Target target = new TargetPermanent().withChooseHint("Reweave");
        this.getSpellAbility().addTarget(target);

        // Splice onto Arcane {2}{U}{U}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{2}{U}{U}"));
    }

    private Reweave(final Reweave card) {
        super(card);
    }

    @Override
    public Reweave copy() {
        return new Reweave(this);
    }
}

class ReweaveEffect extends OneShotEffect {

    private static final FilterPermanentCard filter = new FilterPermanentCard();

    public ReweaveEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target permanent's controller sacrifices it. If the player does, they reveal cards from the top of their library until they reveal a permanent card that shares a card type with the sacrificed permanent, put that card onto the battlefield, then shuffle";
    }

    public ReweaveEffect(final ReweaveEffect effect) {
        super(effect);
    }

    @Override
    public ReweaveEffect copy() {
        return new ReweaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (permanent != null && sourceObject != null) {
            if (permanent.sacrifice(source, game)) {
                Player permanentController = game.getPlayer(permanent.getControllerId());
                if (permanentController != null) {
                    Library library = permanentController.getLibrary();
                    if (library.hasCards()) {
                        Cards cards = new CardsImpl();
                        Card permanentCard = null;
                        for (Card card : permanentController.getLibrary().getCards(game)) {
                            cards.add(card);
                            if (card.isPermanent(game)) {
                                for (CardType cardType : permanent.getCardType(game)) {
                                    if (card.getCardType(game).contains(cardType)) {
                                        permanentCard = card;
                                        break;
                                    }
                                }
                            }
                        }
                        permanentController.revealCards(source, cards, game);
                        if (permanentCard != null) {
                            permanentController.moveCards(permanentCard, Zone.BATTLEFIELD, source, game);
                        }
                        permanentController.shuffleLibrary(source, game);

                    }
                    return true;
                }
                return false;
            }
        }
        return true;
    }
}
