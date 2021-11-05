package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author nantuko
 */
public final class CloneShell extends CardImpl {

    public CloneShell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Imprint - When Clone Shell enters the battlefield, look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CloneShellEffect(), false).setAbilityWord(AbilityWord.IMPRINT));

        // When Clone Shell dies, turn the exiled card face up. If it's a creature card, put it onto the battlefield under your control.
        this.addAbility(new DiesSourceTriggeredAbility(new CloneShellDiesEffect()));
    }

    private CloneShell(final CloneShell card) {
        super(card);
    }

    @Override
    public CloneShell copy() {
        return new CloneShell(this);
    }
}

class CloneShellEffect extends OneShotEffect {

    protected static FilterCard filter1 = new FilterCard("card to exile face down");
    protected static FilterCard filter2 = new FilterCard("card to put on the bottom of your library");

    public CloneShellEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library in any order";
    }

    public CloneShellEffect(CloneShellEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
        if (!cards.isEmpty()) {
            TargetCard target1 = new TargetCard(Zone.LIBRARY, filter1);
            if (controller.choose(Outcome.Benefit, cards, target1, game)) {
                Card card = cards.get(target1.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCardsToExile(card, source, game, false, CardUtil.getCardExileZoneId(game, source), CardUtil.createObjectRealtedWindowTitle(source, game, "(Imprint)"));
                    card.setFaceDown(true, game);
                    Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
                    if (permanent != null) {
                        permanent.imprint(card.getId(), game);
                    }
                }
                target1.clearChosen();
            }
            controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        }
        return true;
    }

    @Override
    public CloneShellEffect copy() {
        return new CloneShellEffect(this);
    }

}

class CloneShellDiesEffect extends OneShotEffect {

    public CloneShellDiesEffect() {
        super(Outcome.Benefit);
        staticText = "turn the exiled card face up. If it's a creature card, put it onto the battlefield under your control";
    }

    public CloneShellDiesEffect(CloneShellDiesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            if (permanent != null) {
                List<UUID> imprinted = permanent.getImprinted();
                if (imprinted != null
                        && !imprinted.isEmpty()) {
                    for (UUID imprintedId : imprinted) {
                        Card imprintedCard = game.getCard(imprintedId);
                        if (imprintedCard != null) {
                            imprintedCard.setFaceDown(false, game);
                            if (imprintedCard.isCreature(game)) {
                                controller.moveCards(imprintedCard, Zone.BATTLEFIELD, source, game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CloneShellDiesEffect copy() {
        return new CloneShellDiesEffect(this);
    }

}
