package mage.cards.h;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HellkiteCourser extends CardImpl {

    public HellkiteCourser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Hellkite Courser enters the battlefield, you may put a commander you own from the command zone onto the battlefield. It gains haste. Return it to the command zone at the beginning of the next end step.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HellkiteCourserEffect(), true));
    }

    private HellkiteCourser(final HellkiteCourser card) {
        super(card);
    }

    @Override
    public HellkiteCourser copy() {
        return new HellkiteCourser(this);
    }
}

class HellkiteCourserEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("commander you own from the command zone");

    static {
        filter.add(CommanderPredicate.instance);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    HellkiteCourserEffect() {
        super(Outcome.Benefit);
        staticText = "put a commander you own from the command zone onto the battlefield. " +
                "It gains haste. Return it to the command zone at the beginning of the next end step";
    }

    private HellkiteCourserEffect(final HellkiteCourserEffect effect) {
        super(effect);
    }

    @Override
    public HellkiteCourserEffect copy() {
        return new HellkiteCourserEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCard(0, 1, Zone.COMMAND, filter);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new HellkiteCourserReturnEffect(permanent, game)
        ), source);
        return true;
    }
}

class HellkiteCourserReturnEffect extends OneShotEffect {

    private final MageObjectReference mor;

    HellkiteCourserReturnEffect(Permanent permanent, Game game) {
        super(Outcome.Benefit);
        this.mor = new MageObjectReference(permanent, game);
        staticText = "return it to the command zone";
    }

    private HellkiteCourserReturnEffect(final HellkiteCourserReturnEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public HellkiteCourserReturnEffect copy() {
        return new HellkiteCourserReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = mor.getPermanent(game);
        if (player == null || permanent == null) {
            return false;
        }
        return player.moveCards(permanent, Zone.COMMAND, source, game);
    }
}
