package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TizerusCharger extends CardImpl {

    public TizerusCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.PEGASUS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Escape—{4}{B}, Exile five other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{4}{B}", 5));

        // Tizerus Charger escapes with your choice of a +1/+1 counter or a flying counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new TizerusChargerEffect(), null, "{this} escapes " +
                "with your choice of a +1/+1 counter or a flying counter on it.", ""
        ));
    }

    private TizerusCharger(final TizerusCharger card) {
        super(card);
    }

    @Override
    public TizerusCharger copy() {
        return new TizerusCharger(this);
    }
}

class TizerusChargerEffect extends OneShotEffect {

    TizerusChargerEffect() {
        super(Outcome.Benefit);
    }

    private TizerusChargerEffect(final TizerusChargerEffect effect) {
        super(effect);
    }

    @Override
    public TizerusChargerEffect copy() {
        return new TizerusChargerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null && source.getAbilityType() == AbilityType.STATIC) {
            permanent = game.getPermanentEntering(source.getSourceId());
        }
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        SpellAbility spellAbility = (SpellAbility) getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
        if (!(spellAbility instanceof EscapeAbility)
                || !spellAbility.getSourceId().equals(source.getSourceId())
                || permanent.getZoneChangeCounter(game) != spellAbility.getSourceObjectZoneChangeCounter()) {
            return false;
        }
        List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");
        CounterType counterType = player.chooseUse(
                outcome, "Choose +1/+1 or flying", null,
                "+1/+1", "Flying", source, game
        ) ? CounterType.P1P1 : CounterType.FLYING;
        permanent.addCounters(counterType.createInstance(), source.getControllerId(), source, game, appliedEffects);
        return true;
    }
}
