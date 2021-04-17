package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CruelRevival extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Zombie creature");
    private static final FilterCard filter2 = new FilterCard("Zombie card from your graveyard");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.not(SubType.ZOMBIE.getPredicate()));
        filter2.add(SubType.ZOMBIE.getPredicate());
    }

    public CruelRevival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Destroy target non-Zombie creature. It can't be regenerated. Return up to one target Zombie card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new CruelRevivalEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filter2));
    }

    private CruelRevival(final CruelRevival card) {
        super(card);
    }

    @Override
    public CruelRevival copy() {
        return new CruelRevival(this);
    }
}

class CruelRevivalEffect extends OneShotEffect {

    CruelRevivalEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target non-Zombie creature. It can't be regenerated. " +
                "Return up to one target Zombie card from your graveyard to your hand";
    }

    private CruelRevivalEffect(final CruelRevivalEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetDestroy = game.getPermanent(source.getFirstTarget());
        if (targetDestroy != null) {
            targetDestroy.destroy(source, game, true);
        }

        Player player = game.getPlayer(source.getControllerId());
        Card targetRetrieve = game.getCard(source.getTargets().get(1).getFirstTarget());
        if (player != null && targetRetrieve != null) {
            player.moveCards(targetRetrieve, Zone.HAND, source, game);
        }
        return true;
    }

    @Override
    public CruelRevivalEffect copy() {
        return new CruelRevivalEffect(this);
    }
}
