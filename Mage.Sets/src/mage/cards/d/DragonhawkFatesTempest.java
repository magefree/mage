package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.Set;
import java.util.UUID;

/**
 * @author notgreat
 */
public final class DragonhawkFatesTempest extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }

    public DragonhawkFatesTempest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Dragonhawk enters or attacks, exile the top X cards of your library, where X is the number of creatures you control with power 4 or greater. You may play those cards until your next end step.
        // At the beginning of your next end step, Dragonhawk deals 2 damage to each opponent for each of those cards that are still exiled.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DragonhawkExileEffect(
                new PermanentsOnBattlefieldCount(filter, null), Duration.UntilYourNextEndStep)));
    }

    private DragonhawkFatesTempest(final DragonhawkFatesTempest card) {
        super(card);
    }

    @Override
    public DragonhawkFatesTempest copy() {
        return new DragonhawkFatesTempest(this);
    }
}

class DragonhawkExileEffect extends ExileTopXMayPlayUntilEffect {

    DragonhawkExileEffect(DynamicValue amount, Duration duration) {
        super(amount, false, duration);
        this.withTextOptions("those cards", true);
        staticText += ". At the beginning of your next end step, " + DragonhawkFatesTempestDamageEffect.STATIC_TEXT;
    }

    private DragonhawkExileEffect(final DragonhawkExileEffect effect) {
        super(effect);
    }

    @Override
    public DragonhawkExileEffect copy() {
        return new DragonhawkExileEffect(this);
    }

    @Override
    protected void effectCards(Game game, Ability source, Set<Card> cards) {
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new DragonhawkFatesTempestDamageEffect(new FixedTargets(cards, game)), TargetController.YOU), source);
    }
}

class DragonhawkFatesTempestDamageEffect extends OneShotEffect {

    FixedTargets cards;
    public static final String STATIC_TEXT = "{this} deals 2 damage to each opponent for each of those cards that are still exiled";

    DragonhawkFatesTempestDamageEffect(FixedTargets cards) {
        super(Outcome.Benefit);
        this.staticText = STATIC_TEXT;
        this.cards = cards;
    }

    private DragonhawkFatesTempestDamageEffect(final DragonhawkFatesTempestDamageEffect effect) {
        super(effect);
        cards = effect.cards;
    }

    @Override
    public DragonhawkFatesTempestDamageEffect copy() {
        return new DragonhawkFatesTempestDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = cards.getTargets(game, source).size(); //Automatically filters out moved cards
        if (count < 1) {
            return false;
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            player = game.getPlayer(playerId);
            if (playerId == null) {
                continue;
            }
            player.damage(count * 2, source.getSourceId(), source, game);
        }
        return true;
    }
}
