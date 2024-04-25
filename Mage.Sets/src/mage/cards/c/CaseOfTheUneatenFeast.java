package mage.cards.c;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.MayCastFromGraveyardSourceAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.CaseSolvedHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.Objects;
import java.util.UUID;

/**
 * @author DominionSpy
 */
public final class CaseOfTheUneatenFeast extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 4) {
        @Override
        public String toString() {
            return "you've gained 5 or more life this turn";
        }
    };

    public CaseOfTheUneatenFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.CASE);

        // Whenever a creature enters the battlefield under your control, you gain 1 life.
        Ability initialAbility = new EntersBattlefieldControlledTriggeredAbility(
                new GainLifeEffect(1), StaticFilters.FILTER_PERMANENT_CREATURE);
        // To solve -- You've gained 5 or more life this turn.
        // Solved -- Sacrifice this Case: Creature cards in your graveyard gain "You may cast this card from your graveyard" until end of turn.
        Ability solvedAbility = new ConditionalActivatedAbility(
                new CaseOfTheUneatenFeastEffect(),
                new SacrificeSourceCost().setText("sacrifice this Case"),
                SolvedSourceCondition.SOLVED);

        this.addAbility(new CaseAbility(initialAbility, condition, solvedAbility)
                        .addHint(new CaseOfTheUneatenFeastHint(condition)),
                new PlayerGainedLifeWatcher());
    }

    private CaseOfTheUneatenFeast(final CaseOfTheUneatenFeast card) {
        super(card);
    }

    @Override
    public CaseOfTheUneatenFeast copy() {
        return new CaseOfTheUneatenFeast(this);
    }
}

class CaseOfTheUneatenFeastEffect extends ContinuousEffectImpl {

    CaseOfTheUneatenFeastEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Creature cards in your graveyard gain \"You may cast this card from your graveyard\" until end of turn";
    }

    private CaseOfTheUneatenFeastEffect(final CaseOfTheUneatenFeastEffect effect) {
        super(effect);
    }

    @Override
    public CaseOfTheUneatenFeastEffect copy() {
        return new CaseOfTheUneatenFeastEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (!getAffectedObjectsSet()) {
            return;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return;
        }
        player.getGraveyard()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(card -> card.isCreature(game))
                .forEach(card -> affectedObjectList.add(new MageObjectReference(card, game)));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.getGraveyard()
                .stream()
                .filter(cardId -> affectedObjectList.contains(new MageObjectReference(cardId, game)))
                .forEach(cardId -> {
                    Card card = game.getCard(cardId);
                    if (card == null) {
                        return;
                    }
                    MayCastFromGraveyardSourceAbility ability = new MayCastFromGraveyardSourceAbility();
                    ability.setSourceId(cardId);
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                });
        return true;
    }
}

class CaseOfTheUneatenFeastHint extends CaseSolvedHint {

    CaseOfTheUneatenFeastHint(Condition condition) {
        super(condition);
    }

    private CaseOfTheUneatenFeastHint(final CaseOfTheUneatenFeastHint hint) {
        super(hint);
    }

    @Override
    public CaseOfTheUneatenFeastHint copy() {
        return new CaseOfTheUneatenFeastHint(this);
    }

    @Override
    public String getConditionText(Game game, Ability ability) {
        int lifeGained = game.getState().getWatcher(PlayerGainedLifeWatcher.class)
                .getLifeGained(ability.getControllerId());
        return "Life gained: " + lifeGained + " (need 5).";
    }
}
