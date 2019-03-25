package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class TheTabernacleAtPendrellVale extends CardImpl {

    public TheTabernacleAtPendrellVale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        addSuperType(SuperType.LEGENDARY);

        // All creatures have "At the beginning of your upkeep, destroy this creature unless you pay {1}."
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new TheTabernacleAtPendrellValeEffect())
        );
    }

    public TheTabernacleAtPendrellVale(final TheTabernacleAtPendrellVale card) {
        super(card);
    }

    @Override
    public TheTabernacleAtPendrellVale copy() {
        return new TheTabernacleAtPendrellVale(this);
    }
}

class TheTabernacleAtPendrellValeEffect extends ContinuousEffectImpl {

    Ability ability = new BeginningOfUpkeepTriggeredAbility(
            new DestroySourceUnlessPaysEffect(
                    new ManaCostsImpl("{1}")),
            TargetController.YOU,
            false);

    public TheTabernacleAtPendrellValeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.AddAbility);
        ability = ability.copy();
        this.ability.newId();
        staticText = "All creatures have \"At the beginning of your upkeep, destroy this creature unless you pay {1}";
    }

    public TheTabernacleAtPendrellValeEffect(final TheTabernacleAtPendrellValeEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public TheTabernacleAtPendrellValeEffect copy() {
        return new TheTabernacleAtPendrellValeEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(CardType.CREATURE)) {
            if (permanent != null) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        if (sublayer == SubLayer.NA
                                && !permanent.getAbilities().contains(ability)) {
                            permanent.addAbility(ability, source.getSourceId(), game);
                        }
                        break;
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
        return layer == Layer.AbilityAddingRemovingEffects_6;
    }
}

class DestroySourceUnlessPaysEffect extends OneShotEffect {

    protected Cost cost;

    public DestroySourceUnlessPaysEffect(Cost cost) {
        super(Outcome.DestroyPermanent);
        this.cost = cost;
    }

    public DestroySourceUnlessPaysEffect(final DestroySourceUnlessPaysEffect effect) {
        super(effect);
        this.cost = effect.cost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null
                && permanent != null
                && source.getSourceObjectZoneChangeCounter() == permanent.getZoneChangeCounter(game)) {
            if (player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + '?', source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    return true;
                }
            }
            permanent.destroy(source.getSourceId(), game, false);
            return true;
        }
        return false;
    }

    @Override
    public DestroySourceUnlessPaysEffect copy() {
        return new DestroySourceUnlessPaysEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "destroy this creature unless you pay {1}";
    }
}
