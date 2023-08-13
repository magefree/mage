
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class Excavator extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("basic land");
    static
    {
        filter.add(SuperType.BASIC.getPredicate());
    }
   
    public Excavator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {tap}, Sacrifice a basic land: Target creature gains landwalk of each of the land types of the sacrificed land until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExcavatorEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Excavator(final Excavator card) {
        super(card);
    }

    @Override
    public Excavator copy() {
        return new Excavator(this);
    }
}

class ExcavatorEffect extends ContinuousEffectImpl {

    private Abilities<Ability> abilities = new AbilitiesImpl();

    public ExcavatorEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        setText("Target creature gains landwalk of each of the land types of the sacrificed land until end of turn");
    }

    public ExcavatorEffect(final ExcavatorEffect effect) {
        super(effect);
        this.abilities = abilities.copy();
    }

    @Override
    public ExcavatorEffect copy() {
        return new ExcavatorEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for(Cost cost : source.getCosts()) {
            if(cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                for(Permanent permanent : sacrificeCost.getPermanents()) {
                    if(permanent.hasSubtype(SubType.FOREST, game))
                    {
                        abilities.add(new ForestwalkAbility());
                    }
                    if(permanent.hasSubtype(SubType.PLAINS, game))
                    {
                        abilities.add(new PlainswalkAbility());
                    }
                    if(permanent.hasSubtype(SubType.ISLAND, game))
                    {
                        abilities.add(new IslandwalkAbility());
                    }
                    if(permanent.hasSubtype(SubType.MOUNTAIN, game))
                    {
                        abilities.add(new MountainwalkAbility());
                    }
                    if(permanent.hasSubtype(SubType.SWAMP, game))
                    {
                        abilities.add(new SwampwalkAbility());
                    }
                }
                
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(permanentId);
            if (permanent != null) {
                for(Ability ability : abilities)
                {
                    permanent.addAbility(ability, source.getSourceId(), game);
                }
            }
        }
        return true;
    }
}
