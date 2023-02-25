package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author @stwalsh4118
 */
public final class RedemptorDreadnought extends CardImpl {

    public RedemptorDreadnought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        
        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.DREADNOUGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Fallen Warrior -- As an additional cost to cast this spell, you may exile a creature card from your graveyard.
        this.getSpellAbility()
            .addCost(new ExileFromGraveCost(
                     new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD), "you may exile a creature card from your graveyard"));

        

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Plasma Incinerator -- Whenever Redemptor Dreadnought attacks, if a card is exiled with it, it gets +X/+X until end of turn, where X is the power of the exiled card.
        Ability ability = new AttacksTriggeredAbility(new RedemptorDreadnoughtEffect().setText("Whenever {this} attacks, if a card is exiled with it, it gets +X/+X until end of turn, where X is the power of the exiled card."), false);
        ability.withFlavorWord("Plasma Incinerator");
        this.addAbility(ability);
    }

    private RedemptorDreadnought(final RedemptorDreadnought card) {
        super(card);
    }

    @Override
    public RedemptorDreadnought copy() {
        return new RedemptorDreadnought(this);
    }
}

class RedemptorDreadnoughtEffect extends OneShotEffect {
    
        public RedemptorDreadnoughtEffect() {
            super(Outcome.Benefit);
            staticText = "if a card is exiled with it, it gets +X/+X until end of turn, where X is the power of the exiled card";
        }
    
        public RedemptorDreadnoughtEffect(final RedemptorDreadnoughtEffect effect) {
            super(effect);
        }
    
        @Override
        public RedemptorDreadnoughtEffect copy() {
            return new RedemptorDreadnoughtEffect(this);
        }
    
        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                int value = 0;
                UUID exileId = CardUtil.getExileZoneId(game, source, -1);
                ExileZone exile = game.getExile().getExileZone(exileId);
                if (exile != null) {
                    for (Card card : exile.getCards(game)) {
                        System.out.println(card.getSpellAbility());
                        value += card.getManaValue();
                        System.out.println(value);
                    }
                }
                System.out.println(value);
                if (value > 0) {
                    ContinuousEffectImpl effect = new BoostSourceEffect(value, value, Duration.EndOfTurn);
                    game.addEffect(effect, source);
                }
                return true;
            }
            return false;
        }
}


class RedemptorDreadnoughtExileCost extends CostImpl {


    List<Permanent> permanents = new ArrayList<>();

    public RedemptorDreadnoughtExileCost(TargetControlledPermanent target) {
        target.setNotTarget(true);
        this.addTarget(target);
        this.text = "exile " + target.getTargetName();
    }

    public RedemptorDreadnoughtExileCost(TargetControlledPermanent target, boolean noText) {
        this.addTarget(target);
    }

    public RedemptorDreadnoughtExileCost(RedemptorDreadnoughtExileCost cost) {
        super(cost);
        for (Permanent permanent : cost.permanents) {
            this.permanents.add(permanent.copy());
        }
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null || !targets.choose(Outcome.Exile, controllerId, source.getSourceId(), source, game)) {
            return paid;
        }
        Cards cards = new CardsImpl();
        for (UUID targetId : targets.get(0).getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                return false;
            }
            cards.add(permanent);
            permanents.add(permanent.copy());
        }

        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        MageObject sourceObject = source.getSourceObject(game);
        player.moveCardsToExile(cards.getCards(game), source, game, true, exileId, sourceObject.getIdName());
        paid = true;
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, source, game);
    }

    @Override
    public RedemptorDreadnoughtExileCost copy() {
        return new RedemptorDreadnoughtExileCost(this);
    }

    public List<Permanent> getPermanents() {
        return permanents;
    }
}