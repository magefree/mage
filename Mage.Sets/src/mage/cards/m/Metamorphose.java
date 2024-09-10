
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author JRHerlehy
 */
public final class Metamorphose extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public Metamorphose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Put target permanent an opponent controls on top of its owner's library. That opponent may put an artifact, creature, enchantment, or land card from their hand onto the battlefield.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new MetamorphoseEffect());

    }

    private Metamorphose(final Metamorphose card) {
        super(card);
    }

    @Override
    public Metamorphose copy() {
        return new Metamorphose(this);
    }
}

class MetamorphoseEffect extends OneShotEffect {
    private static final FilterCard filter = new FilterCard("artifact, creature, enchantment, or land card");

    static{
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public MetamorphoseEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "That opponent may put an artifact, creature, enchantment, or land card from their hand onto the battlefield.";
    }

    private MetamorphoseEffect(final MetamorphoseEffect effect) { super(effect); }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller != null && controller.canRespond() && controller.chooseUse(Outcome.PutCardInPlay, "Put an artifact, creature, enchantment, or land card onto the battlefield?", source, game)) {
                TargetCardInHand target = new TargetCardInHand(filter);
                target.clearChosen();
                if (controller.chooseTarget(outcome, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public MetamorphoseEffect copy() { return new MetamorphoseEffect(this); }
}