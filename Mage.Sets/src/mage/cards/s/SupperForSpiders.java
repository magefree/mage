package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.token.FoodAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author miesma
 */
public final class SupperForSpiders extends CardImpl {

    public SupperForSpiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Put onto the battlefield under your control all creature cards
        // in your opponents' graveyards
        // that were put there from the battlefield this turn.
        // They are Food artifacts with "{2}, {T}, Sacrifice this artifact: You gain 3 life."
        this.getSpellAbility().addEffect(new SupperForSpidersEffect());
        this.getSpellAbility().addWatcher(new CardsPutIntoGraveyardWatcher());
    }

    private SupperForSpiders(final SupperForSpiders card) {
        super(card);
    }

    @Override
    public SupperForSpiders copy() {
        return new SupperForSpiders(this);
    }
}

class SupperForSpidersEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard();

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    SupperForSpidersEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Put onto the battlefield under your control all creature cards " +
                "in your opponents' graveyards " +
                "that were put there from the battlefield this turn. " +
                "They are Food artifacts with \"{2}, {T}, Sacrifice this artifact: You gain 3 life.\" " +
                "<i>(They lose all other types and subtypes.)</i>";
    }

    private SupperForSpidersEffect(final SupperForSpidersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Card> cards = new HashSet<>();
        for (UUID playerUUID : game.getOpponents(source.getControllerId())) {
            // Get creature cards that were put into the graveyard from the battlefield this turn
            Player opponent = game.getPlayer(playerUUID);
            if (opponent != null) {
                cards.addAll(opponent.getGraveyard().getCards(filter, source.getControllerId(), source, game));
            }
        }
        if (!cards.isEmpty()) {
            if (player.moveCards(cards, Zone.BATTLEFIELD, source, game)) {
                // Move to battlefield under control
                for (Card card : cards) {
                    Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
                    if (permanent != null) {
                        // Make Food
                        ContinuousEffect effect = new SupperForSpidersContinuousEffect();
                        effect.setTargetPointer(new FixedTarget(permanent.getId(), game));
                        game.addEffect(effect, source);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public SupperForSpidersEffect copy() {
        return new SupperForSpidersEffect(this);
    }
}

class SupperForSpidersContinuousEffect extends ContinuousEffectImpl {

    private static final Ability ability = new FoodAbility();

    SupperForSpidersContinuousEffect() {
        super(Duration.Custom, Outcome.AddAbility);
        staticText = "They are Food artifacts with “{2}, {T}, Sacrifice this artifact: You gain 3 life.";
    }

    private SupperForSpidersContinuousEffect(final SupperForSpidersContinuousEffect effect) {
        super(effect);
    }

    @Override
    public SupperForSpidersContinuousEffect copy() {
        return new SupperForSpidersContinuousEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent creature;
        if (source.getTargets().getFirstTarget() == null) {
            creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        } else {
            creature = game.getPermanent(source.getTargets().getFirstTarget());
            if (creature == null) {
                creature = game.getPermanentEntering(source.getTargets().getFirstTarget());
            }
        }
        // Food Artifact
        // While they retain their name, mana cost, mana value, and abilities.
        // If any of them are legendary, they remain legendary.
        if (creature != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    creature.retainAllArtifactSubTypes(game);
                    creature.removeAllCardTypes(game);
                    creature.addCardType(game, CardType.ARTIFACT);
                    creature.addSubType(game, SubType.FOOD);
                    return true;
                case AbilityAddingRemovingEffects_6:
                    creature.addAbility(ability, source.getSourceId(), game);
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}

