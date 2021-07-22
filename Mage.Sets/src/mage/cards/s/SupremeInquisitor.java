
package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class SupremeInquisitor extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Wizards you control");

    static {
        filter.add(SubType.WIZARD.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SupremeInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Tap five untapped Wizards you control: Search target player's library for up to five cards and exile them. Then that player shuffles their library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SupremeInquisitorEffect(), new TapTargetCost(new TargetControlledPermanent(5, 5, filter, true)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private SupremeInquisitor(final SupremeInquisitor card) {
        super(card);
    }

    @Override
    public SupremeInquisitor copy() {
        return new SupremeInquisitor(this);
    }
}

class SupremeInquisitorEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    public SupremeInquisitorEffect() {
        super(Outcome.Exile);
        staticText = "Search target player's library for up to five cards and exile them. Then that player shuffles";
    }

    public SupremeInquisitorEffect(final SupremeInquisitorEffect effect) {
        super(effect);
    }

    @Override
    public SupremeInquisitorEffect copy() {
        return new SupremeInquisitorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && targetPlayer != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 5, filter);
            if (player.searchLibrary(target, source, game, targetPlayer.getId())) {
                List<UUID> targetId = target.getTargets();
                for (UUID targetCard : targetId) {
                    Card card = targetPlayer.getLibrary().remove(targetCard, game);
                    if (card != null) {
                        player.moveCardToExileWithInfo(card, null, null, source, game, Zone.LIBRARY, true);
                    }
                }
            }
            targetPlayer.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
