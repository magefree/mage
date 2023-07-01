package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class ShireShirriff extends CardImpl {

    public ShireShirriff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Shire Shirriff enters the battlefield, you may sacrifice a token.
        // When you do, exile target creature an opponent controls until Shire Shirriff leaves the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ShireShirriffEffect(), true));
    }

    private ShireShirriff(final ShireShirriff card) {
        super(card);
    }

    @Override
    public ShireShirriff copy() {
        return new ShireShirriff(this);
    }
}

class ShireShirriffEffect extends OneShotEffect {

    public static final FilterControlledPermanent filterToken = new FilterControlledPermanent("a token");

    static {
        filterToken.add(TokenPredicate.TRUE);
    }

    ShireShirriffEffect() {
        super(Outcome.Benefit);
        this.staticText = "sacrifice a token. When you do, "
            + "exile target creature an opponent controls until {this} leaves the battlefield.";
    }

    private ShireShirriffEffect(final ShireShirriffEffect effect) {
        super(effect);
    }

    @Override
    public ShireShirriffEffect copy() {
        return new ShireShirriffEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Target target = new TargetControlledPermanent(
            1, 1, filterToken, true
        );
        if (!controller.choose(outcome, target, source, game)) {
            return false;
        }

        Permanent toSacrifice = game.getPermanent(target.getFirstTarget());
        if (toSacrifice == null) {
            return false;
        }
        if (!toSacrifice.sacrifice(source, game)) {
            return false;
        }

        ReflexiveTriggeredAbility trigger = new ReflexiveTriggeredAbility(
            new ExileUntilSourceLeavesEffect(), false,
            "exile target creature an opponent controls until {this} leaves the battlefield."
        );
        trigger.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        trigger.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));

        game.fireReflexiveTriggeredAbility(trigger, source);
        return true;
    }
}
