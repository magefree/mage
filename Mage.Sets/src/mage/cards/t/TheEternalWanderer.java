package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.target.TargetPermanent;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DoublestrikeSamuraiToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;


/**
 *
 * @author @stwalsh4118
 */
public final class TheEternalWanderer extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public TheEternalWanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{W}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.setStartingLoyalty(5);

        // No more than one creature can attack The Eternal Wanderer each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TheEternalWandererAttackRestrictionEffect()));

        // +1: Exile up to one target artifact or creature. Return that card to the battlefield under its owner's control at the beginning of that player's next end step.
        Ability ability = new LoyaltyAbility(new TheEternalWandererExileEffect(), 1);
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // 0: Create a 2/2 white Samurai creature token with double strike.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DoublestrikeSamuraiToken()), 0));

        // -4: For each player, choose a creature that player controls. Each player sacrifices all creatures they control not chosen this way.
        this.addAbility(new LoyaltyAbility(new TheEternalWandererSacrificeEffect(), -4));
    }

    private TheEternalWanderer(final TheEternalWanderer card) {
        super(card);
    }

    @Override
    public TheEternalWanderer copy() {
        return new TheEternalWanderer(this);
    }
}

class TheEternalWandererExileEffect extends OneShotEffect {

    public TheEternalWandererExileEffect() {
        super(Outcome.Detriment);
        staticText = "Exile up to one target artifact or creature. Return that card to the battlefield " +
                "under its owner's control at the beginning of that player's next end step.";
    }

    public TheEternalWandererExileEffect(final TheEternalWandererExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            System.out.println(permanent);
            if (permanent != null) {
                UUID exileId = UUID.randomUUID();
                if (controller.moveCardsToExile(permanent, source, game, true, exileId, sourceObject.getIdName())) {
                    //create delayed triggered ability
                    Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                    effect.setTargetPointer(new FixedTarget(permanent.getId(), game));
                    game.addDelayedTriggeredAbility(new AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility(effect, permanent.getOwnerId()), source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public TheEternalWandererExileEffect copy() {
        return new TheEternalWandererExileEffect(this);
    }
}

class TheEternalWandererSacrificeEffect extends OneShotEffect {

    public TheEternalWandererSacrificeEffect() {
        super(Outcome.Detriment);
        staticText = "For each player, choose a creature that player controls. Each player sacrifices all creatures they control not chosen this way";
    }

    public TheEternalWandererSacrificeEffect(final TheEternalWandererSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Permanent> chosenPermanents = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    FilterCreaturePermanent filterCreaturePermanent = new FilterCreaturePermanent("a creature of " + player.getName());
                    filterCreaturePermanent.add(new ControllerIdPredicate(playerId));
                    TargetPermanent target = new TargetPermanent(1, 1, filterCreaturePermanent, true);
                    

                    if (target.canChoose(controller.getId(), source, game)) {
                        controller.chooseTarget(Outcome.Benefit, target, source, game);
                        Permanent creature = game.getPermanent(target.getFirstTarget());
                        if (creature != null) {
                            chosenPermanents.add(creature);
                        }
                        target.clearChosen();
                    }
                }
            }
            // Then each player sacrifices all other nonland permanents they control
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, playerId, game)) {
                        if (!chosenPermanents.contains(permanent)) {
                            permanent.sacrifice(source, game);
                        }
                    }
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public TheEternalWandererSacrificeEffect copy() {
        return new TheEternalWandererSacrificeEffect(this);
    }
}

class TheEternalWandererAttackRestrictionEffect extends RestrictionEffect {

    TheEternalWandererAttackRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "No more than one creature can attack" + 
                     " The Eternal Wanderer each combat";
    }

    TheEternalWandererAttackRestrictionEffect(final TheEternalWandererAttackRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public TheEternalWandererAttackRestrictionEffect copy() {
        return new TheEternalWandererAttackRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {

        if(defenderId == null || attacker == null || source == null){
            return true;
        }

        //Check if attacking The Eternal Wanderer
        if(defenderId.equals(source.getSourceId())){

            //If there is already a creature attacking The Eternal Wanderer, dont let another creature attack it
            for(CombatGroup group : game.getCombat().getGroups()){
                if(group.getDefenderId() != null && group.getDefenderId().equals(source.getSourceId())){
                    return false;
                }
            }
            return true;
        }
        return true;
    }
}
