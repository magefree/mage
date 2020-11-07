package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VolcanicTorrent extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature and planeswalker your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public VolcanicTorrent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Cascade
        this.addAbility(new CascadeAbility());

        // Volcanic Torrent deals X damage to each creature and planeswalker your opponents control, where X is the number of spells you've cast this turn.
        this.getSpellAbility().addEffect(new DamageAllEffect(VolcanicTorrentValue.instance, filter));
    }

    private VolcanicTorrent(final VolcanicTorrent card) {
        super(card);
    }

    @Override
    public VolcanicTorrent copy() {
        return new VolcanicTorrent(this);
    }
}

enum VolcanicTorrentValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return watcher == null ? 0 : watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(sourceAbility.getControllerId());
    }

    @Override
    public VolcanicTorrentValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "the number of spells you've cast this turn";
    }

    @Override
    public String toString() {
        return "X";
    }
}