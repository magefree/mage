package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachTargetToTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NoctisHeirApparent extends CardImpl {

    private static final Condition condition = new IsPhaseCondition(TurnPhase.COMBAT);

    public NoctisHeirApparent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a creature you control enters during combat, you may attach target Equipment you control to target creature you control.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                new AttachTargetToTargetEffect(), StaticFilters.FILTER_CONTROLLED_CREATURE, true
        ).withTriggerCondition(condition);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Warp-Strike -- {3}: Exile Noctis. Return it to the battlefield under its owner's control tapped and attacking at the beginning of that player's next declare attackers step. It can't be blocked that combat.
        this.addAbility(new SimpleActivatedAbility(new NoctisHeirApparentExileEffect(), new GenericManaCost(3)).withFlavorWord("Warp-Strike"));
    }

    private NoctisHeirApparent(final NoctisHeirApparent card) {
        super(card);
    }

    @Override
    public NoctisHeirApparent copy() {
        return new NoctisHeirApparent(this);
    }
}

class NoctisHeirApparentExileEffect extends OneShotEffect {

    NoctisHeirApparentExileEffect() {
        super(Outcome.Benefit);
        staticText = "Exile {this}. Return it to the battlefield under its owner's control tapped and attacking " +
                "at the beginning of that player's next declare attackers step. It can't be blocked that combat.";
    }

    private NoctisHeirApparentExileEffect(final NoctisHeirApparentExileEffect effect) {
        super(effect);
    }

    @Override
    public NoctisHeirApparentExileEffect copy() {
        return new NoctisHeirApparentExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        player.moveCards(permanent, Zone.EXILED, source, game);
        game.addDelayedTriggeredAbility(new NoctisHeirApparentTriggeredAbility(permanent, game), source);
        return true;
    }
}

class NoctisHeirApparentTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID playerId;

    NoctisHeirApparentTriggeredAbility(Permanent permanent, Game game) {
        super(new NoctisHeirApparentReturnEffect(permanent, game), Duration.Custom, true, false);
        this.playerId = permanent.getOwnerId();
    }

    private NoctisHeirApparentTriggeredAbility(final NoctisHeirApparentTriggeredAbility ability) {
        super(ability);
        this.playerId = ability.playerId;
    }

    @Override
    public NoctisHeirApparentTriggeredAbility copy() {
        return new NoctisHeirApparentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(playerId);
    }

    @Override
    public String getRule() {
        return "Return it to the battlefield under its owner's control tapped and attacking at " +
                "the beginning of that player's next declare attackers step. It can't be blocked that combat.";
    }
}

class NoctisHeirApparentReturnEffect extends OneShotEffect {

    NoctisHeirApparentReturnEffect(Permanent permanent, Game game) {
        super(Outcome.Benefit);
        this.setTargetPointer(new FixedTarget(permanent.getId(), game));
    }

    private NoctisHeirApparentReturnEffect(final NoctisHeirApparentReturnEffect effect) {
        super(effect);
    }

    @Override
    public NoctisHeirApparentReturnEffect copy() {
        return new NoctisHeirApparentReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent == null) {
            return true;
        }
        game.getCombat().addAttackingCreature(permanent.getId(), game);
        game.addEffect(new CantBeBlockedTargetEffect(Duration.EndOfCombat)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
