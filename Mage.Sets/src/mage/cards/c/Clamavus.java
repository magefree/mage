package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Clamavus extends CardImpl {

    public Clamavus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TYRANID);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Proclamator Hailer -- Each creature you control gets +1/+1 for each +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new ClamavusEffect()).withFlavorWord("Proclamator Hailer"));
    }

    private Clamavus(final Clamavus card) {
        super(card);
    }

    @Override
    public Clamavus copy() {
        return new Clamavus(this);
    }
}

class ClamavusEffect extends ContinuousEffectImpl {

    public ClamavusEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "each creature you control gets +1/+1 for each +1/+1 counter on it";
    }

    public ClamavusEffect(final ClamavusEffect effect) {
        super(effect);
    }

    @Override
    public ClamavusEffect copy() {
        return new ClamavusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), game
        );
        for (Permanent permanent : permanents) {
            int count = permanent.getCounters(game).getCount(CounterType.P1P1);
            if (count > 0) {
                permanent.addPower(count);
                permanent.addToughness(count);
            }
        }
        return true;
    }
}
