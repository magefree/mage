package mage.cards.i;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfTarkir extends TransformingDoubleFacedCard {

    public InvasionOfTarkir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{1}{R}",
                "Defiant Thundermaw",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "R"
        );

        // Invasion of Tarkir
        this.getLeftHalfCard().setStartingDefense(5);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Tarkir enters the battlefield, reveal any number of Dragon cards from your hand. When you do, Invasion of Tarkir deals X plus 2 damage to any other target, where X is the number of cards revealed this way.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new InvasionOfTarkirEffect()));

        // Defiant Thundermaw
        this.getRightHalfCard().setPT(4, 4);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever a Dragon you control attacks, it deals 2 damage to any target.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(new DefiantThundermawEffect(), false, new FilterControlledCreaturePermanent(SubType.DRAGON));
        ability.addTarget(new TargetAnyTarget());
        this.getRightHalfCard().addAbility(ability);
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
        staticText = "reveal any number of Dragon cards from your hand. When you do, {this} deals X plus 2 damage to any other target, where X is the number of cards revealed this way";
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

class DefiantThundermawEffect extends OneShotEffect {

    DefiantThundermawEffect() {
        super(Outcome.Benefit);
        staticText = "it deals 2 damage to any target";
    }

    private DefiantThundermawEffect(final DefiantThundermawEffect effect) {
        super(effect);
    }

    @Override
    public DefiantThundermawEffect copy() {
        return new DefiantThundermawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObjectReference mor = (MageObjectReference) getValue("attackerRef");
        if (mor == null) {
            return false;
        }
        game.damagePlayerOrPermanent(
                getTargetPointer().getFirst(game, source), 2,
                mor.getSourceId(), source, game, false, true
        );
        return true;
    }
}
