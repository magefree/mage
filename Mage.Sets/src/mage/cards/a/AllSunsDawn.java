
package mage.cards.a;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AllSunsDawn extends CardImpl {

    private static final FilterCard filterGreen = new FilterCard("green card from your graveyard");
    private static final FilterCard filterRed = new FilterCard("red card from your graveyard");
    private static final FilterCard filterBlue = new FilterCard("blue card from your graveyard");
    private static final FilterCard filterBlack = new FilterCard("black card from your graveyard");
    private static final FilterCard filterWhite = new FilterCard("white card from your graveyard");

    static {
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterRed.add(new ColorPredicate(ObjectColor.RED));
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public AllSunsDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // For each color, return up to one target card of that color from your graveyard to your hand.
        this.getSpellAbility().addEffect(new AllSunsDawnEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filterGreen));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filterRed));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filterBlue));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filterBlack));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filterWhite));
        // Exile All Suns' Dawn.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private AllSunsDawn(final AllSunsDawn card) {
        super(card);
    }

    @Override
    public AllSunsDawn copy() {
        return new AllSunsDawn(this);
    }
}

class AllSunsDawnEffect extends OneShotEffect {

    public AllSunsDawnEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "For each color, return up to one target card of that color from your graveyard to your hand";
    }

    private AllSunsDawnEffect(final AllSunsDawnEffect effect) {
        super(effect);
    }

    @Override
    public AllSunsDawnEffect copy() {
        return new AllSunsDawnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToHand = new CardsImpl();
            for (Target target : source.getTargets()) {
                UUID targetId = target.getFirstTarget();
                Card card = game.getCard(targetId);
                if (card != null) {
                    cardsToHand.add(card);
                }
            }
            return controller.moveCards(cardsToHand, Zone.HAND, source, game);
        }
        return false;
    }
}
