package mage.cards.p;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.*;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

/**
 *
 * @author karapuzz14
 */
public final class PoolOfVigorousGrowth extends CardImpl {

    public PoolOfVigorousGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");
        

        // {X}, {T}, Discard a card: Create a token that's a copy of a random creature card with mana value X. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new PoolOfVigorousGrowthEffect(), new ManaCostsImpl<>("{X}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private PoolOfVigorousGrowth(final PoolOfVigorousGrowth card) {
        super(card);
    }

    @Override
    public PoolOfVigorousGrowth copy() {
        return new PoolOfVigorousGrowth(this);
    }
}
class PoolOfVigorousGrowthEffect extends OneShotEffect {

    PoolOfVigorousGrowthEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Create a token that's a copy of a random creature card with mana value X";
    }

    private PoolOfVigorousGrowthEffect(final PoolOfVigorousGrowthEffect effect) {
        super(effect);
    }

    @Override
    public PoolOfVigorousGrowthEffect copy() {
        return new PoolOfVigorousGrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        CardCriteria creatureWithManaValueX = new CardCriteria().types(CardType.CREATURE).manaValue(xValue);
        List<CardInfo> cards = CardRepository.instance.findCards(creatureWithManaValueX);
        if (cards != null) {
            Card randomCard = Objects.requireNonNull(RandomUtil.randomFromCollection(cards)).createCard();
            HashSet<Card> card = new HashSet<>();
            card.add(randomCard);
            game.loadCards(card, player.getId());
            Effect effect = new CreateTokenCopyTargetEffect().setTargetPointer(new FixedTarget(randomCard, game));
            boolean result = effect.apply(game, source);
            game.getCards().remove(randomCard);
            return result;
        }
        return false;
    }
}