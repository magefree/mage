package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VenomEddieBrock extends CardImpl {

    public VenomEddieBrock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SYMBIOTE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever another creature dies, put a +1/+1 counter on Venom. If that creature was a Villain, draw a card.
        Ability ability = new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true
        );
        ability.addEffect(new VenomEddieBrockEffect());
        this.addAbility(ability);
    }

    private VenomEddieBrock(final VenomEddieBrock card) {
        super(card);
    }

    @Override
    public VenomEddieBrock copy() {
        return new VenomEddieBrock(this);
    }
}

class VenomEddieBrockEffect extends OneShotEffect {

    VenomEddieBrockEffect() {
        super(Outcome.Benefit);
        staticText = "If that creature was a Villain, draw a card";
    }

    private VenomEddieBrockEffect(final VenomEddieBrockEffect effect) {
        super(effect);
    }

    @Override
    public VenomEddieBrockEffect copy() {
        return new VenomEddieBrockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) getValue("creatureDied");
        return player != null
                && permanent != null
                && permanent.hasSubtype(SubType.VILLAIN, game)
                && player.drawCards(1, source, game) > 0;
    }
}
