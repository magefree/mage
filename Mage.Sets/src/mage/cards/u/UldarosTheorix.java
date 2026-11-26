package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.assignment.common.CardTypeAssignment;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class UldarosTheorix extends CardImpl {

    public UldarosTheorix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Uldaros Theorix enters, if you cast him, exile up to one target nonland card of each card type
        // from your graveyard. Copy those cards. You may cast any number of spells with total mana value 6 or less
        // from among the copies without paying their mana costs.
        Ability ability = new EntersBattlefieldTriggeredAbility(new UldarosTheorixEffect())
                .withInterveningIf(UldarosTheorixCondition.instance);
        ability.addTarget(new UldarosTheorixTarget());
        this.addAbility(ability);
    }

    private UldarosTheorix(final UldarosTheorix card) {
        super(card);
    }

    @Override
    public UldarosTheorix copy() {
        return new UldarosTheorix(this);
    }
}

enum UldarosTheorixCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CastFromEverywhereSourceCondition.instance.apply(game, source);
    }

    @Override
    public String toString() {
        // same condition as CastFromEverywhereSourceCondition, but the card refers to itself as "him"
        return "you cast him";
    }
}

class UldarosTheorixEffect extends OneShotEffect {

    UldarosTheorixEffect() {
        super(Outcome.PlayForFree);
        staticText = "exile up to one target nonland card of each card type from your graveyard. " +
                "Copy those cards. You may cast any number of spells with total mana value 6 or less " +
                "from among the copies without paying their mana costs. " +
                "<i>(Permanent spells cast this way become tokens.)</i>";
    }

    private UldarosTheorixEffect(final UldarosTheorixEffect effect) {
        super(effect);
    }

    @Override
    public UldarosTheorixEffect copy() {
        return new UldarosTheorixEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        if (cards.isEmpty()) {
            return false;
        }
        Cards copies = new CardsImpl();
        for (Card card : cards.getCards(game)) {
            copies.add(game.copyCard(card, source, source.getControllerId()));
        }
        CardUtil.castMultipleWithAttributeForFree(
                player, source, game, copies, StaticFilters.FILTER_CARD,
                Integer.MAX_VALUE, new UldarosTheorixTracker()
        );
        return true;
    }
}

class UldarosTheorixTracker implements CardUtil.SpellCastTracker {

    private static final int MAX_TOTAL_MANA_VALUE = 6;

    private int totalManaValue = 0;

    @Override
    public boolean checkCard(Card card, Game game) {
        return card.getManaValue() + totalManaValue <= MAX_TOTAL_MANA_VALUE;
    }

    @Override
    public void addCard(Card card, Ability source, Game game) {
        totalManaValue += card.getManaValue();
    }
}

class UldarosTheorixTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter = new FilterNonlandCard("nonland card of each card type");

    private static final CardTypeAssignment cardTypeAssigner = new CardTypeAssignment(
            Arrays.stream(CardType.values())
                    .filter(cardType -> cardType != CardType.LAND)
                    .toArray(CardType[]::new)
    );

    UldarosTheorixTarget() {
        super(0, Integer.MAX_VALUE, filter);
    }

    private UldarosTheorixTarget(final UldarosTheorixTarget target) {
        super(target);
    }

    @Override
    public UldarosTheorixTarget copy() {
        return new UldarosTheorixTarget(this);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);

        // only valid roles
        Cards existingTargets = new CardsImpl(this.getTargets());
        possibleTargets.removeIf(id -> {
            Card card = game.getCard(id);
            if (card == null) {
                return true;
            }
            Cards newTargets = existingTargets.copy();
            newTargets.add(card);
            return cardTypeAssigner.hasSharedRoles(newTargets, game);
        });

        return possibleTargets;
    }
}
