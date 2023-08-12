package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AcererakTheArchlich extends CardImpl {

    public AcererakTheArchlich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Acererak the Archlich enters the battlefield, if you have not completed Tomb of Annihilation, return Acererak the Archlich to its owner's hand and venture into the dungeon.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnToHandSourceEffect(true)),
                AcererakTheArchlichCondition.instance, "When {this} enters the battlefield, " +
                "if you haven't completed Tomb of Annihilation, return {this} " +
                "to its owner's hand and venture into the dungeon."
        );
        ability.addEffect(new VentureIntoTheDungeonEffect());
        this.addAbility(ability.addHint(CompletedDungeonCondition.getHint()), new CompletedDungeonWatcher());

        // Whenever Acererak the Archlich attacks, for each opponent, you create a 2/2 black Zombie creature token unless that player sacrifices a creature.
        this.addAbility(new AttacksTriggeredAbility(new AcererakTheArchlichEffect()));
    }

    private AcererakTheArchlich(final AcererakTheArchlich card) {
        super(card);
    }

    @Override
    public AcererakTheArchlich copy() {
        return new AcererakTheArchlich(this);
    }
}

enum AcererakTheArchlichCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return !CompletedDungeonWatcher.getCompletedNames(
                source.getControllerId(), game
        ).contains("Tomb of Annihilation");
    }
}

class AcererakTheArchlichEffect extends OneShotEffect {

    AcererakTheArchlichEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, you create a 2/2 black Zombie creature " +
                "token unless that player sacrifices a creature";
    }

    private AcererakTheArchlichEffect(final AcererakTheArchlichEffect effect) {
        super(effect);
    }

    @Override
    public AcererakTheArchlichEffect copy() {
        return new AcererakTheArchlichEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int tokens = 0;
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            tokens++;
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetPermanent target = new TargetControlledCreaturePermanent(0, 1);
            target.setNotTarget(true);
            player.choose(Outcome.Sacrifice, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null && permanent.sacrifice(source, game)) {
                tokens--;
            }
        }
        if (tokens > 0) {
            new ZombieToken().putOntoBattlefield(tokens, game, source, source.getControllerId());
        }
        return true;
    }
}
