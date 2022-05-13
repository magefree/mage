package mage.cards.e;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceBasicLandType;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Iterator;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ElsewhereFlask extends CardImpl {

    public ElsewhereFlask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When Elsewhere Flask enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Sacrifice Elsewhere Flask: Choose a basic land type. Each land you control becomes that type until end of turn.
        this.addAbility(new SimpleActivatedAbility(new ElsewhereFlaskEffect(), new SacrificeSourceCost()));
    }

    private ElsewhereFlask(final ElsewhereFlask card) {
        super(card);
    }

    @Override
    public ElsewhereFlask copy() {
        return new ElsewhereFlask(this);
    }
}

class ElsewhereFlaskEffect extends OneShotEffect {

    public ElsewhereFlaskEffect() {
        super(Outcome.Neutral);
        this.staticText = "Choose a basic land type. Each land you control becomes that type until end of turn";
    }

    public ElsewhereFlaskEffect(final ElsewhereFlaskEffect effect) {
        super(effect);
    }

    @Override
    public ElsewhereFlaskEffect copy() {
        return new ElsewhereFlaskEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Choice choice = new ChoiceBasicLandType();
        if (player != null && player.choose(Outcome.Neutral, choice, game)) {
            game.getState().setValue(source.getSourceId().toString() + "_ElsewhereFlask", choice.getChoice());
            game.addEffect(new ElsewhereFlaskContinuousEffect(), source);
            return true;
        }
        return false;
    }
}

class ElsewhereFlaskContinuousEffect extends ContinuousEffectImpl {

    ElsewhereFlaskContinuousEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
    }

    private ElsewhereFlaskContinuousEffect(final ElsewhereFlaskContinuousEffect effect) {
        super(effect);
    }

    @Override
    public ElsewhereFlaskContinuousEffect copy() {
        return new ElsewhereFlaskContinuousEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + "_ElsewhereFlask"));
        switch (choice) {
            case FOREST:
                dependencyTypes.add(DependencyType.BecomeForest);
                break;
            case PLAINS:
                dependencyTypes.add(DependencyType.BecomePlains);
                break;
            case MOUNTAIN:
                dependencyTypes.add(DependencyType.BecomeMountain);
                break;
            case ISLAND:
                dependencyTypes.add(DependencyType.BecomeIsland);
                break;
            case SWAMP:
                dependencyTypes.add(DependencyType.BecomeSwamp);
                break;
        }
        if (this.affectedObjectsSet) {
            game.getBattlefield()
                    .getActivePermanents(
                            StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                            source.getControllerId(), source, game
                    ).stream()
                    .map(permanent -> new MageObjectReference(permanent, game))
                    .forEach(affectedObjectList::add);
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + "_ElsewhereFlask"));
        if (choice == null) {
            return false;
        }
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
            Permanent land = it.next().getPermanent(game);
            if (land == null) {
                it.remove();
                continue;
            }
            land.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
            land.addSubType(game, choice);
            land.removeAllAbilities(source.getSourceId(), game);
            switch (choice) {
                case FOREST:
                    land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                    break;
                case PLAINS:
                    land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                    break;
                case MOUNTAIN:
                    land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                    break;
                case ISLAND:
                    land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                    break;
                case SWAMP:
                    land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                    break;
            }
        }
        return true;
    }
}
