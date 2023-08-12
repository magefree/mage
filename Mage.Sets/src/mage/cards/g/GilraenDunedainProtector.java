package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GilraenDunedainProtector extends CardImpl {

    public GilraenDunedainProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}, {T}: Exile another target creature you control. You may return that card to the battlefield under its owner's control. If you don't, at the beginning of the next end step, return that card to the battlefield under its owner's control with a vigilance counter and a lifelink counter on it.
        ActivatedAbility ability = new SimpleActivatedAbility(
                new GilraenDunedainProtectorEffect(),
                new ManaCostsImpl<>("{2}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());

        this.addAbility(ability);
    }

    private GilraenDunedainProtector(final GilraenDunedainProtector card) {
        super(card);
    }

    @Override
    public GilraenDunedainProtector copy() {
        return new GilraenDunedainProtector(this);
    }
}

class GilraenDunedainProtectorEffect extends OneShotEffect {

    private static final Counters counters = new Counters();

    static {
        counters.addCounter(CounterType.VIGILANCE.createInstance())
                .addCounter(CounterType.LIFELINK.createInstance());
    }

    GilraenDunedainProtectorEffect() {
        super(Outcome.Benefit);
        staticText = "Exile another target creature you control. You may return that card to the battlefield "
                + "under its owner's control. If you don't, at the beginning of the next end step, return "
                + "that card to the battlefield under its owner's control with a vigilance counter and "
                + "a lifelink counter on it.";
    }

    private GilraenDunedainProtectorEffect(final GilraenDunedainProtectorEffect effect) {
        super(effect);
    }

    @Override
    public GilraenDunedainProtectorEffect copy() {
        return new GilraenDunedainProtectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent == null || controller == null || sourcePermanent == null) {
            return false;
        }

        // Exile another target creature you control.
        permanent.moveToExile(source.getSourceId(), sourcePermanent.getName(), source, game);
        game.getState().processAction(game);

        Card card = game.getExile().getCard(permanent.getId(), game);
        boolean choice = controller.chooseUse(Outcome.Neutral, "Return that card to the battlefield now?", source, game);
        if (choice) {
            // return that card to the battlefield under its owner's control.

            if (card != null) {
                PutCards.BATTLEFIELD.moveCards(
                        game.getPlayer(card.getOwnerId()),
                        new CardsImpl(card),
                        source,
                        game
                );
            }
        } else {
            // at the beginning of the next end step, return that card to the battlefield under
            // its owner's control with a vigilance counter and a lifelink counter on it.
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                    new ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect(
                            new MageObjectReference(card, game),
                            counters,
                            "a vigilance counter and a lifelink counter"
                    )
            ), source);
        }

        return true;
    }
}