
package mage.cards.g;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2 & L_J
 */
public final class GauntletsOfChaos extends CardImpl {

    public GauntletsOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // {5}, Sacrifice Gauntlets of Chaos: Exchange control of target artifact, creature, or land you control and target permanent an opponent controls that shares one of those types with it. If those permanents are exchanged this way, destroy all Auras attached to them.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExchangeControlTargetEffect(Duration.EndOfGame, 
            "exchange control of target artifact, creature, or land you control and target permanent an opponent controls that shares one of those types with it."
            + " If those permanents are exchanged this way, destroy all Auras attached to them", false, true, true), 
            new ManaCostsImpl("{5}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new GauntletsOfChaosFirstTarget());
        ability.addTarget(new GauntletsOfChaosSecondTarget());
        this.addAbility(ability);
    }

    public GauntletsOfChaos(final GauntletsOfChaos card) {
        super(card);
    }

    @Override
    public GauntletsOfChaos copy() {
        return new GauntletsOfChaos(this);
    }
}

class GauntletsOfChaosFirstTarget extends TargetControlledPermanent {
    
    public GauntletsOfChaosFirstTarget() {
        super();
        this.filter = this.filter.copy();
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
        setTargetName("artifact, creature, or land you control");
    }  
    
    public GauntletsOfChaosFirstTarget(final GauntletsOfChaosFirstTarget target) {
        super(target);
    }
    
    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            Set<CardType> cardTypes = getOpponentPermanentCardTypes(source.getSourceId(), controllerId, game);
            Permanent permanent  = game.getPermanent(id);
            for (CardType type : permanent.getCardType()) {
                if (cardTypes.contains(type)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        // get all cardtypes from opponents permanents 
        Set<CardType> cardTypes = getOpponentPermanentCardTypes(sourceId, sourceControllerId, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(sourceId);
         for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
             if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                for (CardType type : permanent.getCardType()) {
                    if (cardTypes.contains(type)) {
                        possibleTargets.add(permanent.getId());
                        break;
                    }                    
                }
            }
        }
        return possibleTargets;        
    }
    
    @Override
    public GauntletsOfChaosFirstTarget copy() {
        return new GauntletsOfChaosFirstTarget(this);
    }
    
    private EnumSet<CardType> getOpponentPermanentCardTypes(UUID sourceId, UUID sourceControllerId, Game game) {
        Player controller = game.getPlayer(sourceControllerId);
        EnumSet<CardType> cardTypes =EnumSet.noneOf(CardType.class);
        if (controller != null) {
            for (Permanent permanent: game.getBattlefield().getActivePermanents(sourceControllerId, game)) {
                if (controller.hasOpponent(permanent.getControllerId(), game)) {
                    cardTypes.addAll(permanent.getCardType());
                }
            }
        }
        return cardTypes;
    }
}


class GauntletsOfChaosSecondTarget extends TargetPermanent {

    private Permanent firstTarget = null;
            
    public GauntletsOfChaosSecondTarget() {
        super();
        this.filter = this.filter.copy();
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        setTargetName("permanent an opponent controls that shares one of those types with it");
   }

    public GauntletsOfChaosSecondTarget(final GauntletsOfChaosSecondTarget target) {
        super(target);
        this.firstTarget = target.firstTarget;
    }

    
    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (super.canTarget(id, source, game)) {
            Permanent target1 = game.getPermanent(source.getFirstTarget());
            Permanent opponentPermanent = game.getPermanent(id);
            if (target1 != null && opponentPermanent != null) {
                return target1.shareTypes(opponentPermanent);
            }
        }
        return false;
    }
    
    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        if (firstTarget != null) {
            MageObject targetSource = game.getObject(sourceId);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
                if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    if (permanent.shareTypes(firstTarget)) {
                        possibleTargets.add(permanent.getId());
                    }
                }
            }
        }
        return possibleTargets;
    }
    
    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        firstTarget = game.getPermanent(source.getFirstTarget());
        return super.chooseTarget(Outcome.Damage, playerId, source, game);
    }   
       
    @Override
    public GauntletsOfChaosSecondTarget copy() {
        return new GauntletsOfChaosSecondTarget(this);
    }
}
