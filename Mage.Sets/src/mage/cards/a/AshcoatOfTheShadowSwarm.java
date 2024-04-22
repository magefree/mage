package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class AshcoatOfTheShadowSwarm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.RAT, "Rats you control");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValueHint(
            "Number of Rats you control", xValue
    );

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public AshcoatOfTheShadowSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT, SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Ashcoat of the Shadow Swarm attacks or blocks, other Rats you control
        // get +X/+X until end of turn, where X is the number of Rats you control.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new BoostAllEffect(
                xValue, xValue, Duration.EndOfTurn, filter, true
        ), false).addHint(hint));

        // At the beginning of your end step, you may mill four cards. If you do,
        // return up to two Rat creature cards from your graveyard to your hand.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new MillCardsControllerEffect(4), TargetController.YOU, true
        );
        ability.addEffect(new AshcoatEffect());
        this.addAbility(ability);
    }

    private AshcoatOfTheShadowSwarm(final AshcoatOfTheShadowSwarm card) {
        super(card);
    }

    @Override
    public AshcoatOfTheShadowSwarm copy() {
        return new AshcoatOfTheShadowSwarm(this);
    }
}

class AshcoatEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Rat creature cards");

    static {
        filter.add(SubType.RAT.getPredicate());
    }

    AshcoatEffect() {
        super(Outcome.ReturnToHand);
        staticText = "If you do, return up to two Rat creature cards from your graveyard to your hand.";
    }

    private AshcoatEffect(final AshcoatEffect effect) {
        super(effect);
    }

    @Override
    public AshcoatEffect copy() {
        return new AshcoatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(0, 2, filter, true);
        player.choose(outcome, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
        return true;
    }
}
