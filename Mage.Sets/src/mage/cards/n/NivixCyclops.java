package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class NivixCyclops extends CardImpl {

    public NivixCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        this.subtype.add(SubType.CYCLOPS);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, Nivix Cyclops gets +3/+0 until end of turn and can attack this turn as though it didn't have defender.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(3, 0, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false
        );
        ability.addEffect(new AsThoughNoDefenderEffect());
        this.addAbility(ability);

    }

    public NivixCyclops(final NivixCyclops card) {
        super(card);
    }

    @Override
    public NivixCyclops copy() {
        return new NivixCyclops(this);
    }
}

class AsThoughNoDefenderEffect extends AsThoughEffectImpl {

    public AsThoughNoDefenderEffect() {
        super(AsThoughEffectType.ATTACK, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "and it can attack as though it didn't have defender";
    }

    public AsThoughNoDefenderEffect(final AsThoughNoDefenderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AsThoughNoDefenderEffect copy() {
        return new AsThoughNoDefenderEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Permanent nivixCyclops = game.getPermanent(source.getSourceId());
        if (nivixCyclops != null
                && nivixCyclops.getAbilities().containsKey(DefenderAbility.getInstance().getId())) {
            return true;
        }
        return false;
    }
}
