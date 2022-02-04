package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BloodToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.*;

/**
 * @author Alex-Vasile
 */
public class StrefanMaurerProgenitor extends CardImpl {

    private static final Hint hint = new ValueHint("Players who lost life this turn", StrefanMaurerProgenitorNumberPlayersLostLifeDynamicValue.instance);
    private static final FilterControlledPermanent bloodTokenFilter = new FilterControlledPermanent("Blood tokens");

    static {
        bloodTokenFilter.add(SubType.BLOOD.getPredicate());
        bloodTokenFilter.add(TokenPredicate.TRUE);
    }

    public StrefanMaurerProgenitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, create a Blood token for each player who lost life this turn.
        this.addAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new CreateTokenEffect(
                                new BloodToken(),
                                StrefanMaurerProgenitorNumberPlayersLostLifeDynamicValue.instance),
                        TargetController.YOU,
                        false)
                        .addHint(hint)
        );

        // Whenever Strefan attacks, you may sacrifice two Blood tokens.
        // If you do, you may put a Vampire card from your hand onto the battlefield tapped and attacking.
        // It gains indestructible until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
                new DoIfCostPaid(
                        new StrefanMaurerProgenitorPlayVampireEffect(),
                        new SacrificeTargetCost(new TargetControlledPermanent(
                                2,
                                2,
                                bloodTokenFilter,
                                true)
                        )),
                false
                )
        );
    }

    private StrefanMaurerProgenitor(final StrefanMaurerProgenitor card) { super(card);}

    @Override
    public Card copy() {
        return new StrefanMaurerProgenitor(this);
    }
}

class StrefanMaurerProgenitorPlayVampireEffect extends OneShotEffect {

    private static final FilterCreatureCard vampireCardFilter = new FilterCreatureCard();
    static { vampireCardFilter.add(SubType.VAMPIRE.getPredicate()); }

    public StrefanMaurerProgenitorPlayVampireEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "you may put a Vampire card from your hand onto the battlefield tapped and attacking. " +
                "It gains indestructible until end of turn.";
    }

    private StrefanMaurerProgenitorPlayVampireEffect(final StrefanMaurerProgenitorPlayVampireEffect effect) { super(effect); }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) { return false; }

        TargetCard target = new TargetCardInHand(0, 1, vampireCardFilter);
        if (!player.choose(outcome, player.getHand(), target, game)) { return false; }

        Card card = game.getCard(target.getFirstTarget());
        if (card == null) { return false; }

        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);

        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) { return false; }

        game.getCombat().addAttackingCreature(permanent.getId(), game);

        // Gains indestructable until end of turn
        ContinuousEffect effect = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(permanent.getId(), game));
        game.addEffect(effect, source);

        return true;
    }

    @Override
    public StrefanMaurerProgenitorPlayVampireEffect copy() { return new StrefanMaurerProgenitorPlayVampireEffect(this); }
}

enum StrefanMaurerProgenitorNumberPlayersLostLifeDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller == null) { return 0; }

        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        int numPlayersWhoLostLife = 0;

        if (watcher != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (watcher.getLifeLost(playerId) > 0) {
                    numPlayersWhoLostLife++;
                }
            }
        }

        return numPlayersWhoLostLife;
    }

    @Override
    public DynamicValue copy() { return instance; }

    @Override
    public String getMessage() { return "player who lost life this turn"; }

    @Override
    public String toString() { return "1"; }
}
