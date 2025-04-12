package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author Jmlundeen
 */
public final class AinokStrikeLeader extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature tokens you control");
    private static final FilterCreaturePermanent attackFilter = new FilterCreaturePermanent("this creature and/or your commander");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(TargetController.YOU.getControllerPredicate());

        attackFilter.add(AinokStrikeLeaderPredicate.instance);
    }

    public AinokStrikeLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you attack with this creature and/or your commander, for each opponent, create a 1/1 red Goblin creature token that's tapped and attacking that player.

        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new AinokStrikeLeaderEffect(), 1, attackFilter)
                .setTriggerPhrase("Whenever you attack with " + attackFilter.getMessage() + ", "));

        // Sacrifice this creature: Creature tokens you control gain indestructible until end of turn.
        Effect effect2 = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter);
        this.addAbility(new SimpleActivatedAbility(effect2, new SacrificeSourceCost()));
    }

    private AinokStrikeLeader(final AinokStrikeLeader card) {
        super(card);
    }

    @Override
    public AinokStrikeLeader copy() {
        return new AinokStrikeLeader(this);
    }
}

enum AinokStrikeLeaderPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        if (input.getObject().getId().equals(input.getSourceId())) {
            return input.getObject().getId().equals(input.getSource().getSourceId());
        }
        return CommanderPredicate.instance.apply(input.getObject(), game);

    }

    @Override
    public String toString() {
        return "This creature and/or your commander";
    }
}

class AinokStrikeLeaderEffect extends OneShotEffect {

    public AinokStrikeLeaderEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, create a 1/1 red Goblin creature token that's tapped and attacking that player";
    }

    protected AinokStrikeLeaderEffect(final AinokStrikeLeaderEffect effect) {
        super(effect);
    }

    @Override
    public AinokStrikeLeaderEffect copy() {
        return new AinokStrikeLeaderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            new GoblinToken(false).putOntoBattlefield(1, game, source, source.getControllerId(), true, true, playerId);
        }
        return true;
    }
}