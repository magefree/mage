
package mage.cards.c;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.ElementalToken;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class ChandraFlamecaller extends CardImpl {

    public ChandraFlamecaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{4}{R}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +1: Create two 3/1 red Elemental creature tokens with haste. Exile them at the beginning of the next end step.
        this.addAbility(new LoyaltyAbility(new ChandraElementalEffect(), 1));

        // 0: Discard all the cards in your hand, then draw that many cards plus one.
        this.addAbility(new LoyaltyAbility(new ChandraDrawEffect(), 0));

        // -X: Chandra, Flamecaller deals X damage to each creature.
        this.addAbility(new LoyaltyAbility(new DamageAllEffect(ChandraXValue.getDefault(), StaticFilters.FILTER_PERMANENT_CREATURE)));
    }

    public ChandraFlamecaller(final ChandraFlamecaller card) {
        super(card);
    }

    @Override
    public ChandraFlamecaller copy() {
        return new ChandraFlamecaller(this);
    }
}

class ChandraElementalEffect extends OneShotEffect {

    public ChandraElementalEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create two 3/1 red Elemental creature tokens with haste. Exile them at the beginning of the next end step";
    }

    public ChandraElementalEffect(final ChandraElementalEffect effect) {
        super(effect);
    }

    @Override
    public ChandraElementalEffect copy() {
        return new ChandraElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new ElementalToken("OGW", 1, true), 2);
            effect.apply(game, source);
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }

        return false;
    }
}

class ChandraDrawEffect extends OneShotEffect {

    ChandraDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Discard all the cards in your hand, then draw that many cards plus one";
    }

    ChandraDrawEffect(final ChandraDrawEffect effect) {
        super(effect);
    }

    @Override
    public ChandraDrawEffect copy() {
        return new ChandraDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Set<Card> cardsInHand = player.getHand().getCards(game);
            int amount = cardsInHand.size();
            for (Card card : cardsInHand) {
                player.discard(card, source, game);
            }
            player.drawCards(amount + 1, game);
            return true;
        }
        return false;
    }
}

class ChandraXValue implements DynamicValue {

    private static final ChandraXValue defaultValue = new ChandraXValue();

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                return ((PayVariableLoyaltyCost) cost).getAmount();
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return defaultValue;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }

    public static ChandraXValue getDefault() {
        return defaultValue;
    }
}
