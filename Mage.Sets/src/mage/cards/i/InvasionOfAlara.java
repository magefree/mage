package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.target.common.TargetPermanentAmount;
import mage.target.targetpointer.SecondTargetPointer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfAlara extends TransformingDoubleFacedCard {

    public InvasionOfAlara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{W}{U}{B}{R}{G}",
                "Awaken the Maelstrom",
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "WUBRG"
        );

        // Invasion of Alara
        this.getLeftHalfCard().setStartingDefense(7);

        Ability siegeAbility = new SiegeAbility();
        siegeAbility.setRuleVisible(false);
        this.getLeftHalfCard().addAbility(siegeAbility);

        // When Invasion of Alara enters the battlefield, exile cards from the top of your library until you exile two nonland cards with mana value 4 or less. You may cast one of those two cards without paying its mana cost. Put one of them into your hand. Then put the other cards exiled this way on the bottom of your library in a random order.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new InvasionOfAlaraEffect()));

        // Awaken the Maelstrom
        // Awaken the Maelstrom is all colors.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("{this} is all colors")));

        // Target player draws two cards.
        this.getRightHalfCard().getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer().withChooseHint("to draw two cards"));

        // You may put an artifact card from your hand onto the battlefield.
        this.getRightHalfCard().getSpellAbility().addEffect(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_ARTIFACT_AN));

        // Create a token that's a copy of a permanent you control.
        // Distribute three +1/+1 counters among one, two, or three creatures you control.
        this.getRightHalfCard().getSpellAbility().addEffect(new AwakenTheMaelstromEffect());

        // Destroy target permanent an opponent controls.
        this.getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect().setTargetPointer(new SecondTargetPointer()));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT).withChooseHint("to destroy"));
    }

    private InvasionOfAlara(final InvasionOfAlara card) {
        super(card);
    }

    @Override
    public InvasionOfAlara copy() {
        return new InvasionOfAlara(this);
    }
}

class InvasionOfAlaraEffect extends OneShotEffect {

    InvasionOfAlaraEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile two nonland cards " +
                "with mana value 4 or less. You may cast one of those two cards without paying its mana cost. " +
                "Put one of them into your hand. Then put the other cards exiled this way on the bottom of your library in a random order";
    }

    private InvasionOfAlaraEffect(final InvasionOfAlaraEffect effect) {
        super(effect);
    }

    @Override
    public InvasionOfAlaraEffect copy() {
        return new InvasionOfAlaraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Cards castable = new CardsImpl();
        int count = 0;
        for (Card card : player.getLibrary().getCards(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            cards.add(card);
            if (!card.isLand(game) && card.getManaValue() <= 4) {
                castable.add(card);
                count++;
            }
            if (count >= 2) {
                break;
            }
        }
        CardUtil.castSpellWithAttributesForFree(player, source, game, castable, StaticFilters.FILTER_CARD);
        castable.retainZone(Zone.EXILED, game);
        if (castable.size() > 1) {
            TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD);
            target.withNotTarget(true);
            player.choose(outcome, castable, target, source, game);
            player.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
        } else {
            player.moveCards(castable, Zone.HAND, source, game);
        }
        cards.retainZone(Zone.EXILED, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

class AwakenTheMaelstromEffect extends OneShotEffect {

    AwakenTheMaelstromEffect() {
        super(Outcome.Benefit);
        staticText = "Create a token that's a copy of a permanent you control. " +
                "Distribute three +1/+1 counters among one, two, or three creatures you control.";
    }

    private AwakenTheMaelstromEffect(final AwakenTheMaelstromEffect effect) {
        super(effect);
    }

    @Override
    public AwakenTheMaelstromEffect copy() {
        return new AwakenTheMaelstromEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        makeToken(player, game, source);
        game.processAction();
        distributeCounters(player, game, source);
        return true;
    }

    private void makeToken(Player player, Game game, Ability source) {
        TargetPermanent target = new TargetControlledPermanent();
        target.withNotTarget(true);
        target.withChooseHint("to copy");
        if (!target.canChoose(player.getId(), source, game)) {
            return;
        }
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent != null) {
            new CreateTokenCopyTargetEffect().setSavedPermanent(permanent).apply(game, source);
        }
    }

    private void distributeCounters(Player player, Game game, Ability source) {
        if (game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), source, game) < 1) {
            return;
        }
        TargetPermanentAmount target = new TargetCreaturePermanentAmount(3, StaticFilters.FILTER_CONTROLLED_CREATURE);
        target.withNotTarget(true);
        target.withChooseHint("to distribute counters");
        target.chooseTarget(outcome, player.getId(), source, game);
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(target.getTargetAmount(targetId)), source, game);
            }
        }
    }
}