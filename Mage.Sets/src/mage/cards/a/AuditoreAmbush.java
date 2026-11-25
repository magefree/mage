package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class AuditoreAmbush extends CardImpl {

    public AuditoreAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Target player searches their library and/or graveyard for a card named Ezio, Blade of Vengeance, reveals it, and puts it into their hand. If they search their library this way, they shuffle.
        Mode mode = new Mode(new AuditoreAmbushEffect());
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private AuditoreAmbush(final AuditoreAmbush card) {
        super(card);
    }

    @Override
    public AuditoreAmbush copy() {
        return new AuditoreAmbush(this);
    }
}

class AuditoreAmbushEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Ezio, Blade of Vengeance");

    static {
        filter.add(new NamePredicate(filter.getMessage()));
    }

    AuditoreAmbushEffect() {
        super(Outcome.Benefit);
        staticText = "target player searches their library and/or graveyard for a card named Ezio, Blade of Vengeance, "
                + "reveals it, and puts it into their hand. If they search their library this way, they shuffle";
    }

    private AuditoreAmbushEffect(final AuditoreAmbushEffect effect) {
        super(effect);
    }

    @Override
    public AuditoreAmbushEffect copy() {
        return new AuditoreAmbushEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // the targeted player is the one who searches and chooses
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (player == null || sourceObject == null) {
            return false;
        }
        Card cardFound = null;
        boolean needShuffle = false;
        if (player.chooseUse(outcome, "Search your library for a card named " + filter.getMessage() + '?', source, game)) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
            if (player.searchLibrary(target, source, game)) {
                cardFound = game.getCard(target.getFirstTarget());
            }
            needShuffle = true;
        }
        if (cardFound == null && player.chooseUse(outcome, "Search your graveyard for a card named " + filter.getMessage() + '?', source, game)) {
            TargetCard target = new TargetCardInYourGraveyard(0, 1, filter, true);
            if (player.choose(outcome, player.getGraveyard(), target, source, game)) {
                cardFound = game.getCard(target.getFirstTarget());
            }
        }
        if (cardFound != null) {
            player.revealCards(sourceObject.getIdName(), new CardsImpl(cardFound), game);
            player.moveCards(cardFound, Zone.HAND, source, game);
        }
        if (needShuffle) {
            player.shuffleLibrary(source, game);
        }
        return true;
    }
}
