package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author weirddan455
 */
public final class Endurance extends CardImpl {

    private static final FilterCard filter = new FilterCard("a green card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public Endurance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Endurance enters the battlefield, up to one target player puts all the cards from their graveyard on the bottom of their library in a random order.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EnduranceEffect());
        ability.addTarget(new TargetPlayer(0, 1, false));
        this.addAbility(ability);

        // Evoke—Exile a green card from your hand.
        this.addAbility(new EvokeAbility(new ExileFromHandCost(new TargetCardInHand(filter))));
    }

    private Endurance(final Endurance card) {
        super(card);
    }

    @Override
    public Endurance copy() {
        return new Endurance(this);
    }
}

class EnduranceEffect extends OneShotEffect {

    public EnduranceEffect() {
        super(Outcome.Detriment);
        this.staticText = "up to one target player puts all the cards from their graveyard on the bottom of their library in a random order";
    }

    private EnduranceEffect(final EnduranceEffect effect) {
        super(effect);
    }

    @Override
    public EnduranceEffect copy() {
        return new EnduranceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        return targetPlayer != null && targetPlayer.putCardsOnBottomOfLibrary(targetPlayer.getGraveyard(), game, source, false);
    }
}
