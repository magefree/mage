package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class LilianaDeathMage extends CardImpl {

    public LilianaDeathMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{B}{B}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);
        this.setStartingLoyalty(4);

        // +1: Return up to one target creature card from your graveyard to your hand.
        Ability plusAbility = new LoyaltyAbility(new LilianaDeathMagePlusEffect(), 1);
        plusAbility.addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(plusAbility);

        // −3: Destroy target creature. Its controller loses 2 life.
        Ability minusAbility = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        minusAbility.addTarget(new TargetCreaturePermanent());
        minusAbility.addEffect(new LoseLifeTargetControllerEffect(2));
        this.addAbility(minusAbility);

        // −7: Target opponent loses 2 life for each creature card in their graveyard.
        Ability ultimateAbility = new LoyaltyAbility(new LilianaDeathMageUltimateEffect(), -7);
        ultimateAbility.addTarget(new TargetOpponent());
        this.addAbility(ultimateAbility);
    }

    private LilianaDeathMage(final LilianaDeathMage card) {
        super(card);
    }

    @Override
    public LilianaDeathMage copy() {
        return new LilianaDeathMage(this);
    }
}

class LilianaDeathMagePlusEffect extends OneShotEffect {

    LilianaDeathMagePlusEffect() {
        super(Outcome.Benefit);
        staticText = "Return up to one target creature card from your graveyard to your hand";
    }

    private LilianaDeathMagePlusEffect(LilianaDeathMagePlusEffect effect) {
        super(effect);
    }

    @Override
    public LilianaDeathMagePlusEffect copy() {
        return new LilianaDeathMagePlusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = game.getCard(source.getTargets().get(0).getFirstTarget());
        if (card == null) {
            return false;
        }
        return player.moveCards(card, Zone.HAND, source, game);
    }
}

class LilianaDeathMageUltimateEffect extends OneShotEffect {

    LilianaDeathMageUltimateEffect() {
        super(Outcome.Damage);
        staticText = "Target opponent loses 2 life for each creature card in their graveyard";
    }

    private LilianaDeathMageUltimateEffect(LilianaDeathMageUltimateEffect effect) {
        super(effect);
    }

    @Override
    public LilianaDeathMageUltimateEffect copy() {
        return new LilianaDeathMageUltimateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent != null) {
            int amount = opponent.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
            opponent.loseLife(amount * 2, game, source, false);
        }
        return true;
    }
}