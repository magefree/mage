package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HydroidKrasis extends CardImpl {

    public HydroidKrasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{U}");

        this.subtype.add(SubType.JELLYFISH);
        this.subtype.add(SubType.HYDRA);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // When you cast this spell, you gain half X life and draw half X cards. Round down each time.
        this.addAbility(new CastSourceTriggeredAbility(new HydroidKrasisEffect(), false));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Hydroid Krasis enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));
    }

    private HydroidKrasis(final HydroidKrasis card) {
        super(card);
    }

    @Override
    public HydroidKrasis copy() {
        return new HydroidKrasis(this);
    }
}

class HydroidKrasisEffect extends OneShotEffect {

    HydroidKrasisEffect() {
        super(Outcome.Benefit);
        staticText = "you gain half X life and draw half X cards. Round down each time.";
    }

    private HydroidKrasisEffect(final HydroidKrasisEffect effect) {
        super(effect);
    }

    @Override
    public HydroidKrasisEffect copy() {
        return new HydroidKrasisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Object obj = getValue(CastSourceTriggeredAbility.SOURCE_CAST_SPELL_ABILITY);
        if (!(obj instanceof SpellAbility)) {
            return false;
        }
        int halfCost = Math.floorDiv(((SpellAbility) obj).getManaCostsToPay().getX(), 2);
        player.drawCards(halfCost, source, game);
        player.gainLife(halfCost, game, source);
        return true;
    }
}
