package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterEnchantmentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MaskToken;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class EstridTheMasked extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();
    private static final FilterPermanent filter2 = new FilterPermanent("another permanent");

    static {
        filter.add(new EnchantedPredicate());
        filter2.add(new AnotherPredicate());
    }

    public EstridTheMasked(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{G}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ESTRID);
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +2: Untap each enchanted permanent you control.
        this.addAbility(new LoyaltyAbility(new UntapAllControllerEffect(
                filter, "untap each enchanted permanent you control"
        ), 2));

        // -1: Create a white Aura enchantment token named Mask attached to another target permanent. The token has enchant permanent and totem armor.
        Ability ability = new LoyaltyAbility(
                new EstridTheMaskedTokenEffect(), -1
        );
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);

        // -7: Put the top seven cards of your library into your graveyard. Return all non-Aura enchantment cards from your graveyard to the battlefield, then do the same for Aura cards.
        this.addAbility(new LoyaltyAbility(
                new EstridTheMaskedGraveyardEffect(), -7
        ));

        // Estrid, the Masked can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    public EstridTheMasked(final EstridTheMasked card) {
        super(card);
    }

    @Override
    public EstridTheMasked copy() {
        return new EstridTheMasked(this);
    }
}

class EstridTheMaskedTokenEffect extends OneShotEffect {

    public EstridTheMaskedTokenEffect() {
        super(Outcome.Benefit);
        this.staticText = "create a white Aura enchantment token named Mask "
                + "attached to another target permanent. "
                + "The token has enchant permanent and totem armor";
    }

    public EstridTheMaskedTokenEffect(final EstridTheMaskedTokenEffect effect) {
        super(effect);
    }

    @Override
    public EstridTheMaskedTokenEffect copy() {
        return new EstridTheMaskedTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new MaskToken());
        effect.apply(game, source);
        for (UUID tokenId : effect.getLastAddedTokenIds()) {
            Permanent token = game.getPermanent(tokenId);
            if (token == null) {
                continue;
            }
            token.attachTo(source.getFirstTarget(), game);
        }
        return true;
    }
}

class EstridTheMaskedGraveyardEffect extends OneShotEffect {

    private static final FilterEnchantmentCard filter
            = new FilterEnchantmentCard();
    private static final FilterEnchantmentCard filter2
            = new FilterEnchantmentCard();

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.AURA)));
        filter.add(new SubtypePredicate(SubType.AURA));
    }

    public EstridTheMaskedGraveyardEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "put the top seven cards of your library "
                + "into your graveyard. Return all non-Aura enchantment cards "
                + "from your graveyard to the battlefield, "
                + "then do the same for Aura cards";
    }

    public EstridTheMaskedGraveyardEffect(final EstridTheMaskedGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public EstridTheMaskedGraveyardEffect copy() {
        return new EstridTheMaskedGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new PutTopCardOfLibraryIntoGraveControllerEffect(7).apply(game, source);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.moveCards(controller.getGraveyard().getCards(
                filter, source.getSourceId(), source.getControllerId(), game
        ), Zone.BATTLEFIELD, source, game);
        controller.moveCards(controller.getGraveyard().getCards(
                filter2, source.getSourceId(), source.getControllerId(), game
        ), Zone.BATTLEFIELD, source, game);
        return true;
    }
}
