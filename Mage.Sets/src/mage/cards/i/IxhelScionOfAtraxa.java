package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.common.ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class IxhelScionOfAtraxa extends CardImpl {

    public IxhelScionOfAtraxa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN, SubType.ANGEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Toxic 2
        this.addAbility(new ToxicAbility(2));

        // Corrupted — At the beginning of your end step, each opponent who has three or more poison counters
        // exiles the top card of their library face down. You may look at and play those cards for as long as
        // they remain exiled, and you may spend mana as though it were mana of any color to cast those spells.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(
                new IxhelScionOfAtraxaEffect(), false).setAbilityWord(AbilityWord.CORRUPTED)
        );
    }

    private IxhelScionOfAtraxa(final IxhelScionOfAtraxa card) {
        super(card);
    }

    @Override
    public IxhelScionOfAtraxa copy() {
        return new IxhelScionOfAtraxa(this);
    }
}

class IxhelScionOfAtraxaEffect extends OneShotEffect {

    IxhelScionOfAtraxaEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent who has three or more poison counters exiles the top card of their " +
                "library face down. You may look at and play those cards for as long as they remain exiled, " +
                "and you may spend mana as though it were mana of any color to cast those spells";
    }

    private IxhelScionOfAtraxaEffect(final IxhelScionOfAtraxaEffect effect) {
        super(effect);
    }

    @Override
    public IxhelScionOfAtraxaEffect copy() {
        return new IxhelScionOfAtraxaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = new CardsImpl();
        for (UUID opponentId : game.getOpponents(source.getControllerId(), true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || opponent.getCountersCount(CounterType.POISON) < 3 || !opponent.getLibrary().hasCards()) {
                continue;
            }
            Card card = opponent.getLibrary().getFromTop(game);
            if (card != null) {
                cards.add(card);
            }
        }
        if (cards.isEmpty()) {
            return false;
        }
        return new ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect(false, CastManaAdjustment.AS_THOUGH_ANY_MANA_COLOR)
                .setTargetPointer(new FixedTargets(cards, game))
                .apply(game, source);
    }
}