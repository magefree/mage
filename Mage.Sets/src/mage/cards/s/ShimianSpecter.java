package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ShimianSpecter extends CardImpl {

    public ShimianSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.SPECTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Shimian Specter deals combat damage to a player, that player 
        // reveals their hand. You choose a nonland card from it. Search that player's 
        // graveyard, hand, and library for all cards with the same name as that card 
        // and exile them. Then that player shuffles their library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ShimianSpecterEffect(), false, true));
    }

    private ShimianSpecter(final ShimianSpecter card) {
        super(card);
    }

    @Override
    public ShimianSpecter copy() {
        return new ShimianSpecter(this);
    }
}

class ShimianSpecterEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    private static final FilterCard filter = new FilterCard("nonland card");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public ShimianSpecterEffect() {
        super(false, "that player's", "all cards with the same name as that card");
        staticText = "that player reveals their hand. You choose a nonland card from it. "
                + "Search that player's graveyard, hand, and library for all cards "
                + "with the same name as that card and exile them. Then that "
                + "player shuffles";
    }

    private ShimianSpecterEffect(final ShimianSpecterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (targetPlayer != null
                && sourceObject != null
                && controller != null) {

            // reveal hand of target player
            targetPlayer.revealCards(sourceObject.getName(), targetPlayer.getHand(), game);

            // You choose a nonland card from it
            TargetCard target = new TargetCard(Zone.HAND, new FilterNonlandCard());
            target.withNotTarget(true);
            if (target.canChoose(controller.getId(), source, game)
                    && controller.chooseTarget(Outcome.Benefit, targetPlayer.getHand(), target, source, game)) {
                return applySearchAndExile(game, source, CardUtil.getCardNameForSameNameSearch(game.getCard(target.getFirstTarget())), getTargetPointer().getFirst(game, source));
            }
        }
        return false;
    }

    @Override
    public ShimianSpecterEffect copy() {
        return new ShimianSpecterEffect(this);
    }

}
