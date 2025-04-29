package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeraldOfIlharg extends CardImpl {

    public HeraldOfIlharg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast a creature spell, put two +1/+1 counters on Herald of Ilharg. If that spell has mana value 5 or greater, Herald of Ilharg deals damage equal to the number of counters on it to each opponent.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                StaticFilters.FILTER_SPELL_A_CREATURE, false
        );
        ability.addEffect(new HeraldOfIlhargEffect());
        this.addAbility(ability);
    }

    private HeraldOfIlharg(final HeraldOfIlharg card) {
        super(card);
    }

    @Override
    public HeraldOfIlharg copy() {
        return new HeraldOfIlharg(this);
    }
}

class HeraldOfIlhargEffect extends OneShotEffect {

    HeraldOfIlhargEffect() {
        super(Outcome.Benefit);
        staticText = "If that spell has mana value 5 or greater, " +
                "{this} deals damage equal to the number of counters on it to each opponent";
    }

    private HeraldOfIlhargEffect(final HeraldOfIlhargEffect effect) {
        super(effect);
    }

    @Override
    public HeraldOfIlhargEffect copy() {
        return new HeraldOfIlhargEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(permanent -> permanent.getCounters(game))
                .map(Counters::getTotalCount)
                .orElse(0);
        Spell spell = (Spell) getValue("spellCast");
        if (count < 1 || spell == null || spell.getManaValue() < 5) {
            return false;
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.damage(count, source, game);
            }
        }
        return true;
    }
}
