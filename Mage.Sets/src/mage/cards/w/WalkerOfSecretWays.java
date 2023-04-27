package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WalkerOfSecretWays extends CardImpl {

    private static final FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("Ninja you control");

    static {
        filterCreature.add((SubType.NINJA.getPredicate()));
    }

    public WalkerOfSecretWays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Ninjutsu {1}{U} ({1}{U}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility("{1}{U}"));

        // Whenever Walker of Secret Ways deals combat damage to a player, look at that player's hand.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new WalkerOfSecretWaysEffect(), false, true));

        // {1}{U}: Return target Ninja you control to its owner's hand. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{1}{U}"), MyTurnCondition.instance);
        ability.addTarget(new TargetControlledCreaturePermanent(1, 1, filterCreature, false));
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);


    }

    private WalkerOfSecretWays(final WalkerOfSecretWays card) {
        super(card);
    }

    @Override
    public WalkerOfSecretWays copy() {
        return new WalkerOfSecretWays(this);
    }
}

class WalkerOfSecretWaysEffect extends OneShotEffect {
    WalkerOfSecretWaysEffect() {
        super(Outcome.Detriment);
        staticText = "look at that player's hand";
    }

    WalkerOfSecretWaysEffect(final WalkerOfSecretWaysEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null && controller != null) {
            controller.lookAtCards("Walker of Secret Ways", player.getHand(), game);
        }
        return true;
    }

    @Override
    public WalkerOfSecretWaysEffect copy() {
        return new WalkerOfSecretWaysEffect(this);
    }

}