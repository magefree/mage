package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CursedScroll extends CardImpl {

    public CursedScroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {3}, {T}: Name a card. Reveal a card at random from your hand. If it's the named card, Cursed Scroll deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL), new ManaCostsImpl("{3}"));
        ability.addEffect(new CursedScrollEffect());
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private CursedScroll(final CursedScroll card) {
        super(card);
    }

    @Override
    public CursedScroll copy() {
        return new CursedScroll(this);
    }
}

class CursedScrollEffect extends OneShotEffect {

    public CursedScrollEffect() {
        super(Outcome.Neutral);
        staticText = ", then reveal a card at random from your hand. If that card has the chosen name, {this} deals 2 damage to any target";
    }

    public CursedScrollEffect(final CursedScrollEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (sourceObject != null && controller != null && cardName != null && !cardName.isEmpty()) {
            if (!controller.getHand().isEmpty()) {
                Cards revealed = new CardsImpl();
                Card card = controller.getHand().getRandom(game);
                if (card == null) {
                    return false;
                }
                revealed.add(card);
                controller.revealCards(sourceObject.getIdName(), revealed, game);
                if (CardUtil.haveSameNames(card, cardName, game)) {
                    Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
                    if (creature != null) {
                        creature.damage(2, source.getSourceId(), source, game, false, true);
                        return true;
                    }
                    Player player = game.getPlayer(targetPointer.getFirst(game, source));
                    if (player != null) {
                        player.damage(2, source.getSourceId(), source, game);
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CursedScrollEffect copy() {
        return new CursedScrollEffect(this);
    }
}
