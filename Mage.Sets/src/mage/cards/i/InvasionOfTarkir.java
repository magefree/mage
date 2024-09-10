package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfTarkir extends CardImpl {

    public InvasionOfTarkir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{1}{R}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(5);
        this.secondSideCardClazz = mage.cards.d.DefiantThundermaw.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Tarkir enters the battlefield, reveal any number of Dragon cards from your hand. When you do, Invasion of Tarkir deals X plus 2 damage to any other target, where X is the number of cards revealed this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvasionOfTarkirEffect()));
    }

    private InvasionOfTarkir(final InvasionOfTarkir card) {
        super(card);
    }

    @Override
    public InvasionOfTarkir copy() {
        return new InvasionOfTarkir(this);
    }
}

class InvasionOfTarkirEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Dragon cards");
    private static final FilterPermanentOrPlayer filter2 = new FilterAnyTarget("any other target");

    static {
        filter.add(SubType.DRAGON.getPredicate());
        filter2.getPermanentFilter().add(AnotherPredicate.instance);
    }

    InvasionOfTarkirEffect() {
        super(Outcome.Benefit);
        staticText = "reveal any number of Dragon cards from your hand. When you do, " +
                "{this} deals X plus 2 damage to any other target, where X is the number of cards revealed this way";
    }

    private InvasionOfTarkirEffect(final InvasionOfTarkirEffect effect) {
        super(effect);
    }

    @Override
    public InvasionOfTarkirEffect copy() {
        return new InvasionOfTarkirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
        player.choose(Outcome.Benefit, player.getHand(), target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        player.revealCards(source, cards, game);
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(cards.size() + 2), false
        );
        ability.addTarget(new TargetPermanentOrPlayer(filter2));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
