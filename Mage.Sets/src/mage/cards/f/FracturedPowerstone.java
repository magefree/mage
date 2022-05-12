package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.abilities.Abilities;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.game.command.CommandObject;
import mage.game.command.Plane;
import mage.game.stack.StackAbility;
import mage.watchers.common.PlanarRollWatcher;

public final class FracturedPowerstone extends CardImpl {

    public FracturedPowerstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Add roll planar die.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new FracturedPowerstoneEffect(), new TapSourceCost()
        );
        this.addAbility(ability);
    }

    public FracturedPowerstone(final FracturedPowerstone card) {
        super(card);
    }

    @Override
    public FracturedPowerstone copy() {
        return new FracturedPowerstone(this);
    }
}

class FracturedPowerstoneEffect extends OneShotEffect {

    FracturedPowerstoneEffect() {
        super(Outcome.Benefit);
        staticText = "Roll the planar";
    }

    private FracturedPowerstoneEffect(final FracturedPowerstoneEffect effect) {
        super(effect);
    }

    @Override
    public FracturedPowerstoneEffect copy() {
        return new FracturedPowerstoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for(CommandObject commandObject : game.getState().getCommand()){
            if(commandObject instanceof Plane){
                Abilities<Ability> abilities = commandObject.getAbilities();
                for(Ability ability : abilities){
                    if(ability instanceof ActivateIfConditionActivatedAbility){
                        StackAbility stackAbility = new StackAbility(ability,game.getActivePlayerId());
                        stackAbility.createCopyOnStack(game, source, source.getControllerId(), true);
                        
                        PlanarRollWatcher watcher = game.getState().getWatcher(PlanarRollWatcher.class);
                        watcher.removePlanarDieRoll(game.getActivePlayerId());
                        return true;
                    }
                }
            }
        }
        return false;
    }
}