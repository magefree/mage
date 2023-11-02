package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MalcolmAlluringScoundrel extends CardImpl {

    public MalcolmAlluringScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SIREN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Malcolm, Alluring Scoundrel deals combat damage to a player, put a chorus counter on it. Draw a card, then discard a card. If there are four or more chorus counters on Malcolm, you may cast the discarded card without paying its mana cost.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHORUS.createInstance()), false
        );
        ability.addEffect(new MalcolmAlluringScoundrelEffect());
        this.addAbility(ability);
    }

    private MalcolmAlluringScoundrel(final MalcolmAlluringScoundrel card) {
        super(card);
    }

    @Override
    public MalcolmAlluringScoundrel copy() {
        return new MalcolmAlluringScoundrel(this);
    }
}

class MalcolmAlluringScoundrelEffect extends OneShotEffect {

    MalcolmAlluringScoundrelEffect() {
        super(Outcome.Benefit);
        staticText = "Draw a card, then discard a card. "
                + "If there are four or more chorus counters on {this}, you may cast the discarded card without paying its mana cost.";
    }

    private MalcolmAlluringScoundrelEffect(final MalcolmAlluringScoundrelEffect effect) {
        super(effect);
    }

    @Override
    public MalcolmAlluringScoundrelEffect copy() {
        return new MalcolmAlluringScoundrelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        controller.drawCards(1, source, game);
        Cards cards = controller.discard(1, false, false, source, game);

        Permanent malcolm = source.getSourcePermanentOrLKI(game);
        if (!cards.isEmpty() && malcolm != null && malcolm.getCounters(game).getCount(CounterType.CHORUS) >= 4) {
            CardUtil.castSpellWithAttributesForFree(controller, source, game, cards, StaticFilters.FILTER_CARD);
        }

        return true;
    }

}
