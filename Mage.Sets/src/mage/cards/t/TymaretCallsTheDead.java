package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TymaretCallsTheDead extends CardImpl {

    public TymaretCallsTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Put the top three cards of your library into your graveyard. Then you may exile a creature or enchantment card from your graveyard. If you do, create a 2/2 black Zombie creature token.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, new TymaretCallsTheDeadFirstEffect()
        );

        // III — You gain X life and scry X, where X is the number of Zombies you control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TymaretCallsTheDeadLastEffect());

        this.addAbility(sagaAbility);
    }

    private TymaretCallsTheDead(final TymaretCallsTheDead card) {
        super(card);
    }

    @Override
    public TymaretCallsTheDead copy() {
        return new TymaretCallsTheDead(this);
    }
}

class TymaretCallsTheDeadFirstEffect extends OneShotEffect {

    private static final Effect millEffect = new MillCardsControllerEffect(3);
    private static final Effect tokenEffect = new CreateTokenEffect(new ZombieToken());
    private static final FilterCard filter = new FilterCard("creature or enchantment card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    TymaretCallsTheDeadFirstEffect() {
        super(Outcome.Benefit);
        staticText = "mill three cards. Then you may exile a creature or enchantment card from your graveyard. " +
                "If you do, create a 2/2 black Zombie creature token";
    }

    private TymaretCallsTheDeadFirstEffect(final TymaretCallsTheDeadFirstEffect effect) {
        super(effect);
    }

    @Override
    public TymaretCallsTheDeadFirstEffect copy() {
        return new TymaretCallsTheDeadFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        millEffect.apply(game, source);
        if (player.getGraveyard().count(filter, game) == 0
                || !player.chooseUse(Outcome.Exile, "Exile a creature or enchantment card from your graveyard?", source, game)) {
            return true;
        }
        TargetCard target = new TargetCardInYourGraveyard(filter);
        target.setNotTarget(true);
        if (!player.choose(outcome, player.getGraveyard(), target, source, game)) {
            return true;
        }
        return player.moveCards(game.getCard(target.getFirstTarget()), Zone.EXILED, source, game)
                && tokenEffect.apply(game, source);
    }
}

class TymaretCallsTheDeadLastEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ZOMBIE, "");

    TymaretCallsTheDeadLastEffect() {
        super(Outcome.Benefit);
        staticText = "You gain X life and scry X, where X is the number of Zombies you control.";
    }

    private TymaretCallsTheDeadLastEffect(final TymaretCallsTheDeadLastEffect effect) {
        super(effect);
    }

    @Override
    public TymaretCallsTheDeadLastEffect copy() {
        return new TymaretCallsTheDeadLastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int zombieCount = game.getBattlefield().countAll(filter, source.getControllerId(), game);
        if (zombieCount <= 0) {
            return true;
        }
        player.gainLife(zombieCount, game, source);
        player.scry(zombieCount, source, game);
        return true;
    }
}
