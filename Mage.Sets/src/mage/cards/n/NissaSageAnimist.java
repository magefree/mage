package mage.cards.n;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.NissaSageAnimistToken;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class NissaSageAnimist extends CardImpl {

    public NissaSageAnimist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NISSA);
        this.color.setGreen(true);

        this.nightCard = true;

        this.setStartingLoyalty(3);

        // +1: Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.
        this.addAbility(new LoyaltyAbility(new NissaSageAnimistPlusOneEffect(), 1));

        // -2: Create a legendary 4/4 green Elemental creature token named Ashaya, the Awoken World.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new NissaSageAnimistToken()), -2));

        // -7: Untap up to six target lands. They become 6/6 Elemental creatures. They're still lands.
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), -7);
        ability.addTarget(new TargetLandPermanent(0, 6, StaticFilters.FILTER_LAND, false));
        ability.addEffect(new NissaSageAnimistMinusAnimateEffect());
        this.addAbility(ability);
    }

    private NissaSageAnimist(final NissaSageAnimist card) {
        super(card);
    }

    @Override
    public NissaSageAnimist copy() {
        return new NissaSageAnimist(this);
    }
}

class NissaSageAnimistPlusOneEffect extends OneShotEffect {

    NissaSageAnimistPlusOneEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.";
    }

    NissaSageAnimistPlusOneEffect(final NissaSageAnimistPlusOneEffect effect) {
        super(effect);
    }

    @Override
    public NissaSageAnimistPlusOneEffect copy() {
        return new NissaSageAnimistPlusOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
            Zone targetZone = Zone.HAND;
            if (card.isLand(game)) {
                targetZone = Zone.BATTLEFIELD;
            }
            return controller.moveCards(card, targetZone, source, game);
        }
        return true;
    }
}

class NissaSageAnimistMinusAnimateEffect extends OneShotEffect {

    public NissaSageAnimistMinusAnimateEffect() {
        super(Outcome.BecomeCreature);
        this.staticText = "They become 6/6 Elemental creatures. They're still lands";
    }

    public NissaSageAnimistMinusAnimateEffect(final NissaSageAnimistMinusAnimateEffect effect) {
        super(effect);
    }

    @Override
    public NissaSageAnimistMinusAnimateEffect copy() {
        return new NissaSageAnimistMinusAnimateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                ContinuousEffectImpl effect = new BecomesCreatureTargetEffect(new CreatureToken(6, 6, "", SubType.ELEMENTAL), false, true, Duration.Custom);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
