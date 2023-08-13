package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.GetXLoyaltyValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.ElementalTokenWithHaste;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ChandraFlamecaller extends CardImpl {

    public ChandraFlamecaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);

        this.setStartingLoyalty(4);

        // +1: Create two 3/1 red Elemental creature tokens with haste. Exile them at the beginning of the next end step.
        this.addAbility(new LoyaltyAbility(new ChandraElementalEffect(), 1));

        // 0: Discard all the cards in your hand, then draw that many cards plus one.
        this.addAbility(new LoyaltyAbility(new ChandraDrawEffect(), 0));

        // -X: Chandra, Flamecaller deals X damage to each creature.
        this.addAbility(new LoyaltyAbility(new DamageAllEffect(
                GetXLoyaltyValue.instance, StaticFilters.FILTER_PERMANENT_CREATURE
        )));
    }

    private ChandraFlamecaller(final ChandraFlamecaller card) {
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
            CreateTokenEffect effect = new CreateTokenEffect(new ElementalTokenWithHaste(), 2);
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
        if (player == null) {
            return false;
        }
        int amount = player.discard(player.getHand(), false, source, game).size();
        player.drawCards(amount + 1, source, game);
        return true;
    }
}
