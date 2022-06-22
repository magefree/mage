
package mage.cards.j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author L_J
 */
public final class JalumGrifter extends CardImpl {

    public JalumGrifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {1}{R}, {T}: Shuffle Jalum Grifter and two lands you control face down. Target opponent chooses one of those cards. Turn the cards face up. If they chose Jalum Grifter, sacrifice it. Otherwise, destroy target permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JalumGrifterEffect(), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private JalumGrifter(final JalumGrifter card) {
        super(card);
    }

    @Override
    public JalumGrifter copy() {
        return new JalumGrifter(this);
    }
}

class JalumGrifterEffect extends OneShotEffect {

    public JalumGrifterEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Shuffle {this} and two lands you control face down. Target opponent chooses one of those cards. Turn the cards face up. If they chose {this}, sacrifice it. Otherwise, destroy target permanent";
    }

    public JalumGrifterEffect(final JalumGrifterEffect effect) {
        super(effect);
    }

    @Override
    public JalumGrifterEffect copy() {
        return new JalumGrifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getTargets().get(0).getFirstTarget());
        if (controller != null && opponent != null) {
            List<Card> shellGamePile = new ArrayList<>();
            Card sourceCard = game.getCard(source.getSourceId());
            if (sourceCard != null) {
                sourceCard = sourceCard.copy();
                sourceCard.setFaceDown(true, game);
                shellGamePile.add(sourceCard);
                game.informPlayers(controller.getLogName() + " turns " + sourceCard.getLogName() + " face down");
            }
            
            Target target = new TargetControlledPermanent(2, 2, new FilterControlledLandPermanent(), true);
            if (target.canChoose(controller.getId(), source, game)) {
                while (!target.isChosen() && target.canChoose(controller.getId(), source, game) && controller.canRespond()) {
                    controller.chooseTarget(outcome, target, source, game);
                }
            }
            
            for (UUID cardId: target.getTargets()) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    card = card.copy();
                    card.setFaceDown(true, game);
                    shellGamePile.add(card);
                    game.informPlayers(controller.getLogName() + " turns " + card.getLogName() + " face down");
                }
            }
            if (shellGamePile.isEmpty()) {
                return true;
            }
            Collections.shuffle(shellGamePile);
            game.informPlayers(controller.getLogName() + " shuffles the face-down pile");
            TargetCard targetCard = new TargetCard(Zone.HAND, new FilterCard());
            CardsImpl cards = new CardsImpl();
            cards.addAll(shellGamePile);
            if (opponent.choose(Outcome.Sacrifice, cards, targetCard, game)) {
                Card card = game.getCard(targetCard.getFirstTarget());
                if (card != null) {
                    card.setFaceDown(false, game);
                    game.informPlayers(opponent.getLogName() + " reveals " + card.getLogName());
                    if (card.getId().equals(sourceCard.getId())) {
                        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                        if (sourcePermanent != null) {
                            sourcePermanent.sacrifice(source, game);
                        }
                    } else {
                        Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
                        if (permanent != null) {
                            permanent.destroy(source, game, false);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
