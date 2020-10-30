
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.common.DeliriumHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class EmrakulThePromisedEnd extends CardImpl {

    private static final FilterCard filter = new FilterCard("instants");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public EmrakulThePromisedEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{13}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(13);
        this.toughness = new MageInt(13);

        // Emrakul, the Promised End costs {1} less to cast for each card type among cards in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new EmrakulThePromisedEndCostReductionEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability.addHint(DeliriumHint.instance));

        // When you cast Emrakul, you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn.
        ability = new CastSourceTriggeredAbility(new EmrakulThePromisedEndGainControlEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Protection from instants
        this.addAbility(new ProtectionAbility(filter));
    }

    public EmrakulThePromisedEnd(final EmrakulThePromisedEnd card) {
        super(card);
    }

    @Override
    public EmrakulThePromisedEnd copy() {
        return new EmrakulThePromisedEnd(this);
    }
}

class EmrakulThePromisedEndCostReductionEffect extends CostModificationEffectImpl {

    EmrakulThePromisedEndCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "{this} costs {1} less to cast for each card type among cards in your graveyard";
    }

    EmrakulThePromisedEndCostReductionEffect(EmrakulThePromisedEndCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<CardType> foundCardTypes = new HashSet<>(8);
            for (Card card : controller.getGraveyard().getCards(game)) {
                foundCardTypes.addAll(card.getCardType());
            }
            CardUtil.reduceCost(abilityToModify, foundCardTypes.size());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId())
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public EmrakulThePromisedEndCostReductionEffect copy() {
        return new EmrakulThePromisedEndCostReductionEffect(this);
    }
}

class EmrakulThePromisedEndGainControlEffect extends OneShotEffect {

    EmrakulThePromisedEndGainControlEffect() {
        super(Outcome.GainControl);
        this.staticText = "you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn";
    }

    EmrakulThePromisedEndGainControlEffect(final EmrakulThePromisedEndGainControlEffect effect) {
        super(effect);
    }

    @Override
    public EmrakulThePromisedEndGainControlEffect copy() {
        return new EmrakulThePromisedEndGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            TurnMod controlPlayerTurnMod = new TurnMod(targetPlayer.getId(), controller.getId());
            TurnMod extraTurnMod = new TurnMod(targetPlayer.getId(), false);
            controlPlayerTurnMod.setSubsequentTurnMod(extraTurnMod);
            game.getState().getTurnMods().add(controlPlayerTurnMod);
            return true;
        }
        return false;
    }
}
