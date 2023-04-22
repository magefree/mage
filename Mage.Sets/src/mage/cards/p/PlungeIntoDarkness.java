
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.PayVariableLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class PlungeIntoDarkness extends CardImpl {

    public PlungeIntoDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Sacrifice any number of creatures, then you gain 3 life for each sacrificed creature;
        this.getSpellAbility().addEffect(new PlungeIntoDarknessLifeEffect());
        // or pay X life, then look at the top X cards of your library, put one of those cards into your hand, and exile the rest.
        Mode mode = new Mode(new PlungeIntoDarknessSearchEffect());
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {B}
        this.addAbility(new EntwineAbility("{B}"));
    }

    private PlungeIntoDarkness(final PlungeIntoDarkness card) {
        super(card);
    }

    @Override
    public PlungeIntoDarkness copy() {
        return new PlungeIntoDarkness(this);
    }
}

class PlungeIntoDarknessLifeEffect extends OneShotEffect {

    PlungeIntoDarknessLifeEffect() {
        super(Outcome.GainLife);
        this.staticText = "Sacrifice any number of creatures, then you gain 3 life for each sacrificed creature";
    }

    PlungeIntoDarknessLifeEffect(final PlungeIntoDarknessLifeEffect effect) {
        super(effect);
    }

    @Override
    public PlungeIntoDarknessLifeEffect copy() {
        return new PlungeIntoDarknessLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, new FilterControlledCreaturePermanent(), true);
            player.chooseTarget(Outcome.Sacrifice, target, source, game);
            int numSacrificed = 0;
            for (UUID permanentId : target.getTargets()) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    if (permanent.sacrifice(source, game)) {
                        numSacrificed++;
                    }
                }
            }
            if (numSacrificed > 0) {
                player.gainLife(3 * numSacrificed, game, source);
            }
            return true;
        }
        return false;
    }
}

class PlungeIntoDarknessSearchEffect extends OneShotEffect {

    PlungeIntoDarknessSearchEffect() {
        super(Outcome.Benefit);
        this.staticText = "pay X life, then look at the top X cards of your library, put one of those cards into your hand, and exile the rest.";
    }

    PlungeIntoDarknessSearchEffect(final PlungeIntoDarknessSearchEffect effect) {
        super(effect);
    }

    @Override
    public PlungeIntoDarknessSearchEffect copy() {
        return new PlungeIntoDarknessSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            VariableCost cost = new PayVariableLifeCost();
            int xValue = cost.announceXValue(source, game);
            cost.getFixedCostsFromAnnouncedValue(xValue).pay(source, game, source, source.getControllerId(), false, null);
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, xValue));
            controller.lookAtCards(source, null, cards, game);

            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put into your hand"));
            if (controller.choose(Outcome.DrawCard, cards, target, source, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCards(card, Zone.HAND, source, game);
                }
            }
            controller.moveCards(cards, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }
}
