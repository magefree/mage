package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KayasWrath extends CardImpl {

    public KayasWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{W}{B}{B}");

        // Destroy all creatures. You gain life equal to the number of creatures you controlled that were destroyed this way.
        this.getSpellAbility().addEffect(new KayasWrathEffect());
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);
    }

    private KayasWrath(final KayasWrath card) {
        super(card);
    }

    @Override
    public KayasWrath copy() {
        return new KayasWrath(this);
    }
}

class KayasWrathEffect extends OneShotEffect {

    KayasWrathEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy all creatures. You gain life equal to the number of " +
                "creatures you controlled that were destroyed this way.";
    }

    private KayasWrathEffect(final KayasWrathEffect effect) {
        super(effect);
    }

    @Override
    public KayasWrathEffect copy() {
        return new KayasWrathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int counter = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (permanent != null) {
                boolean isMine = permanent.isControlledBy(source.getControllerId());
                if (permanent.destroy(source, game, false) && isMine) {
                    counter++;
                }
            }
        }
        return new GainLifeEffect(counter).apply(game, source);
    }
}