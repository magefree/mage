
package mage.cards.v;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class VeneratedTeacher extends CardImpl {

    public VeneratedTeacher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Venerated Teacher enters the battlefield, put two level counters on each creature you control with level up.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VeneratedTeacherEffect()));
    }

    private VeneratedTeacher(final VeneratedTeacher card) {
        super(card);
    }

    @Override
    public VeneratedTeacher copy() {
        return new VeneratedTeacher(this);
    }
}

class VeneratedTeacherEffect extends OneShotEffect {

    public VeneratedTeacherEffect() {
        super(Outcome.BoostCreature);
        staticText = "put two level counters on each creature you control with level up";
    }

    public VeneratedTeacherEffect(final VeneratedTeacherEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURES, source.getControllerId(), game);
        if (!permanents.isEmpty()) {
            for (Permanent permanent : permanents) {
                for (Ability ability : permanent.getAbilities()) {
                    if (ability instanceof LevelUpAbility) {
                        permanent.addCounters(CounterType.LEVEL.createInstance(2), source.getControllerId(), source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public VeneratedTeacherEffect copy() {
        return new VeneratedTeacherEffect(this);
    }
}
