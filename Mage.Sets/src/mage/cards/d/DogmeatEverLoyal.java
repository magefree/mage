package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.permanent.token.JunkToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DogmeatEverLoyal extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("a creature you control that's enchanted or equipped");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(Predicates.or(
                EnchantedPredicate.instance,
                EquippedPredicate.instance
        ));
    }

    public DogmeatEverLoyal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Dogmeat enters the battlefield, mill five cards, then return an Aura or Equipment card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(5));
        ability.addEffect(new DogmeatEverLoyalEffect());
        this.addAbility(ability);

        // Whenever a creature you control that's enchanted or equipped attacks, create a Junk token.
        this.addAbility(new AttacksAllTriggeredAbility(
                new CreateTokenEffect(new JunkToken()), false,
                filter, SetTargetPointer.NONE, false
        ));
    }

    private DogmeatEverLoyal(final DogmeatEverLoyal card) {
        super(card);
    }

    @Override
    public DogmeatEverLoyal copy() {
        return new DogmeatEverLoyal(this);
    }
}

class DogmeatEverLoyalEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Aura or Equipment card");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    DogmeatEverLoyalEffect() {
        super(Outcome.Benefit);
        staticText = ", then return an Aura or Equipment card from your graveyard to your hand";
    }

    private DogmeatEverLoyalEffect(final DogmeatEverLoyalEffect effect) {
        super(effect);
    }

    @Override
    public DogmeatEverLoyalEffect copy() {
        return new DogmeatEverLoyalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(filter, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(filter);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}
