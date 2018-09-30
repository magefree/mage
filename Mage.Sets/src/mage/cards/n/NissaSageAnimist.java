
package mage.cards.n;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.NissaSageAnimistToken;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

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

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(3));

        // +1: Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.
        this.addAbility(new LoyaltyAbility(new NissaSageAnimistPlusOneEffect(), 1));

        // -2: Create a legendary 4/4 green Elemental creature token named Ashaya, the Awoken World.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new NissaSageAnimistToken()), -2));

        // -7: Untap up to six target lands. They become 6/6 Elemental creatures. They're still lands.
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), -7);
        ability.addTarget(new TargetLandPermanent(0, 6, StaticFilters.FILTER_LANDS, false));
        ability.addEffect(new NissaSageAnimistMinusSevenEffect());
        this.addAbility(ability);
    }

    public NissaSageAnimist(final NissaSageAnimist card) {
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
            if (card.isLand()) {
                targetZone = Zone.BATTLEFIELD;
            }
            return controller.moveCards(card, targetZone, source, game);
        }
        return true;
    }
}

class NissaSageAnimistMinusSevenEffect extends ContinuousEffectImpl {

    NissaSageAnimistMinusSevenEffect() {
        super(Duration.EndOfGame, Outcome.BecomeCreature);
        this.staticText = "They become 6/6 Elemental creatures. They're still lands";
    }

    NissaSageAnimistMinusSevenEffect(final NissaSageAnimistMinusSevenEffect effect) {
        super(effect);
    }

    @Override
    public NissaSageAnimistMinusSevenEffect copy() {
        return new NissaSageAnimistMinusSevenEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (UUID permanentId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                            permanent.addCardType(CardType.CREATURE);
                        if (!permanent.hasSubtype(SubType.ELEMENTAL, game)) {
                            permanent.getSubtype(game).add(SubType.ELEMENTAL);
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getToughness().setValue(6);
                            permanent.getPower().setValue(6);
                        }
                }
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.PTChangingEffects_7;
    }
}
