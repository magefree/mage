
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterTeamPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class ThrasherBrute extends CardImpl {

    private static final ThrasherBruteFilter filter = new ThrasherBruteFilter();

    public ThrasherBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Thrasher Brute or another Warrior enters the battlefield under your team's control, target opponent loses 1 life and you gain 1 life.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new LoseLifeTargetEffect(1),
                filter,
                false,
                "Whenever {this} or another Warrior enters the battlefield under your team's control, "
                + "target opponent loses 1 life and you gain 1 life."
        );
        ability.addEffect(new GainLifeEffect(1));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public ThrasherBrute(final ThrasherBrute card) {
        super(card);
    }

    @Override
    public ThrasherBrute copy() {
        return new ThrasherBrute(this);
    }
}

class ThrasherBruteFilter extends FilterTeamPermanent {

    ThrasherBruteFilter() {
        super();
    }

    ThrasherBruteFilter(final ThrasherBruteFilter effect) {
        super(effect);
    }

    @Override
    public ThrasherBruteFilter copy() {
        return new ThrasherBruteFilter(this);
    }

    @Override
    public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
        if (super.match(permanent, sourceId, playerId, game)) {
            if (sourceId.equals(permanent.getId())) {
                return true;
            } else {
                if (permanent.hasSubtype(SubType.WARRIOR, game)) {
                    return true;
                }
            }
        }
        return false;
    }

}
