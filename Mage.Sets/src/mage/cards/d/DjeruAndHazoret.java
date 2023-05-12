package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HeckbentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DjeruAndHazoret extends CardImpl {

    public DjeruAndHazoret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // As long as you have one or fewer cards in hand, Djeru and Hazoret has vigilance and haste.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance()), HeckbentCondition.instance,
                "as long as you have one or fewer cards in hand, {this} has vigilance"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HasteAbility.getInstance()),
                HeckbentCondition.instance, "and haste"
        ));
        this.addAbility(ability);

        // Whenever Djeru and Hazoret attacks, look at the top six cards of your library. You may exile a legendary creature card from among them. Put the rest on the bottom of your library in a random order. Until end of turn, you may cast the exiled card without paying its mana cost.
        this.addAbility(new AttacksTriggeredAbility(new DjeruAndHazoretEffect()));
    }

    private DjeruAndHazoret(final DjeruAndHazoret card) {
        super(card);
    }

    @Override
    public DjeruAndHazoret copy() {
        return new DjeruAndHazoret(this);
    }
}

class DjeruAndHazoretEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard("a legendary creature card");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    DjeruAndHazoretEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top six cards of your library. You may exile a legendary creature card " +
                "from among them. Put the rest on the bottom of your library in a random order. " +
                "Until end of turn, you may cast the exiled card without paying its mana cost";
    }

    private DjeruAndHazoretEffect(final DjeruAndHazoretEffect effect) {
        super(effect);
    }

    @Override
    public DjeruAndHazoretEffect copy() {
        return new DjeruAndHazoretEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        TargetCard target = new TargetCardInLibrary(0, 1, filter);
        player.choose(Outcome.PlayForFree, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.EXILED, source, game);
            game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(
                    Zone.EXILED, TargetController.YOU, Duration.Custom, true
            ).setTargetPointer(new FixedTarget(card, game)), source);
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
