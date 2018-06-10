
package mage.cards.g;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author BetaSteward
 */
public final class GrimoireOfTheDead extends CardImpl {

    public GrimoireOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        addSuperType(SuperType.LEGENDARY);

        // {1}, {tap}, Discard a card: Put a study counter on Grimoire of the Dead.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STUDY.createInstance()), new ManaCostsImpl("{1}"));
        ability1.addCost(new TapSourceCost());
        ability1.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(ability1);

        // {tap}, Remove three study counters from Grimoire of the Dead and sacrifice it: Put all creature cards from all graveyards onto the battlefield under your control. They're black Zombies in addition to their other colors and types.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrimoireOfTheDeadEffect(), new TapSourceCost());
        ability2.addCost(new RemoveCountersSourceCost(CounterType.STUDY.createInstance(3)));
        ability2.addCost(new SacrificeSourceCost());
        this.addAbility(ability2);

    }

    public GrimoireOfTheDead(final GrimoireOfTheDead card) {
        super(card);
    }

    @Override
    public GrimoireOfTheDead copy() {
        return new GrimoireOfTheDead(this);
    }
}

class GrimoireOfTheDeadEffect extends OneShotEffect {

    public GrimoireOfTheDeadEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put all creature cards in all graveyards onto the battlefield under your control. They're black Zombies in addition to their other colors and types";
    }

    public GrimoireOfTheDeadEffect(final GrimoireOfTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> creatureCards = new LinkedHashSet<>();
            for (Player player : game.getPlayers().values()) {
                for (Card card : player.getGraveyard().getCards(game)) {
                    if (card.isCreature()) {
                        creatureCards.add(card);
                        game.addEffect(new GrimoireOfTheDeadEffect2(card.getId()), source);
                    }
                }
            }
            controller.moveCards(creatureCards, Zone.BATTLEFIELD, source, game, false, false, false, null);
            return true;
        }
        return false;
    }

    @Override
    public GrimoireOfTheDeadEffect copy() {
        return new GrimoireOfTheDeadEffect(this);
    }

}

class GrimoireOfTheDeadEffect2 extends ContinuousEffectImpl {

    private final UUID targetId;

    public GrimoireOfTheDeadEffect2(UUID targetId) {
        super(Duration.Custom, Outcome.Neutral);
        this.targetId = targetId;
        staticText = "Becomes a black Zombie in addition to its other colors and types";
    }

    public GrimoireOfTheDeadEffect2(final GrimoireOfTheDeadEffect2 effect) {
        super(effect);
        this.targetId = effect.targetId;
    }

    @Override
    public GrimoireOfTheDeadEffect2 copy() {
        return new GrimoireOfTheDeadEffect2(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            switch (layer) {
                case ColorChangingEffects_5:
                    if (sublayer == SubLayer.NA) {
                        permanent.getColor(game).setBlack(true);
                    }
                    break;
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        permanent.getSubtype(game).add(SubType.ZOMBIE);
                    }
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

}
