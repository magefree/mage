
package mage.cards.t;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
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
 * @author emerald000
 */
public final class Terraformer extends CardImpl {

    public Terraformer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}: Choose a basic land type. Each land you control becomes that type until end of turn.
        this.addAbility(new SimpleActivatedAbility(new TerraformerEffect(), new GenericManaCost(1)));
    }

    private Terraformer(final Terraformer card) {
        super(card);
    }

    @Override
    public Terraformer copy() {
        return new Terraformer(this);
    }
}

class TerraformerEffect extends OneShotEffect {

    TerraformerEffect() {
        super(Outcome.Neutral);
        this.staticText = "Choose a basic land type. Each land you control becomes that type until end of turn";
    }

    private TerraformerEffect(final TerraformerEffect effect) {
        super(effect);
    }

    @Override
    public TerraformerEffect copy() {
        return new TerraformerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Choice choice = new ChoiceBasicLandType();
            if (player.choose(Outcome.Neutral, choice, game)) {
                game.getState().setValue(source.getSourceId().toString() + "_Terraformer", choice.getChoice());
            }
            game.addEffect(new TerraformerContinuousEffect(), source);
            return true;
        }
        return false;
    }
}

class TerraformerContinuousEffect extends ContinuousEffectImpl {

    TerraformerContinuousEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
    }

    private TerraformerContinuousEffect(final TerraformerContinuousEffect effect) {
        super(effect);
    }

    @Override
    public TerraformerContinuousEffect copy() {
        return new TerraformerContinuousEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + "_Terraformer"));
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
