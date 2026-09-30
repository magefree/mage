package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author muz
 */
public final class BlackWidowSuperSpy extends CardImpl {

    public BlackWidowSuperSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Black Widow deals combat damage to a player, that player exiles cards from the top of their library until they exile a nonland card. You may put a +1/+1 counter on Black Widow. If you don't, you may cast the exiled nonland card until end of turn and mana of any type can be spent to cast that spell.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new BlackWidowSuperSpyEffect(), false, true));
    }

    private BlackWidowSuperSpy(final BlackWidowSuperSpy card) {
        super(card);
    }

    @Override
    public BlackWidowSuperSpy copy() {
        return new BlackWidowSuperSpy(this);
    }
}

class BlackWidowSuperSpyEffect extends OneShotEffect {

    BlackWidowSuperSpyEffect() {
        super(Outcome.Benefit);
        staticText = "that player exiles cards from the top of their library until they exile a nonland card. "
                + "You may put a +1/+1 counter on Black Widow. If you don't, you may cast the exiled nonland card "
                + "until end of turn and mana of any type can be spent to cast that spell";
    }

    private BlackWidowSuperSpyEffect(final BlackWidowSuperSpyEffect effect) {
        super(effect);
    }

    @Override
    public BlackWidowSuperSpyEffect copy() {
        return new BlackWidowSuperSpyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player damagedPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || damagedPlayer == null) {
            return false;
        }

        Card cardToCast = null;
        for (Card card : damagedPlayer.getLibrary().getCards(game)) {
            damagedPlayer.moveCards(card, Zone.EXILED, source, game);
            if (!card.isLand(game)) {
                cardToCast = card;
                break;
            }
        }

        Permanent blackWidow = source.getSourcePermanentIfItStillExists(game);
        if (blackWidow != null && controller.chooseUse(outcome, "Put a +1/+1 counter on Black Widow?", source, game)) {
            blackWidow.addCounters(CounterType.P1P1.createInstance(), source, game);
            return true;
        }

        CardUtil.makeCardPlayable(game, source, cardToCast, true, Duration.EndOfTurn, true);
        return true;
    }
}
