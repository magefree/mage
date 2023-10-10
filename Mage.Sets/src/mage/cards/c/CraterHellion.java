
package mage.cards.c;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Backfir3
 */
public final class CraterHellion extends CardImpl {

    public CraterHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.HELLION);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Echo 4RR (At the beginning of your upkeep, if this came under your control since the beginning of your last upkeep, sacrifice it unless you pay its echo cost.)
        this.addAbility(new EchoAbility("{4}{R}{R}"));

        //When Crater Hellion enters the battlefield, it deals 4 damage to each other creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CraterHellionEffect(), false));
    }

    private CraterHellion(final CraterHellion card) {
        super(card);
    }

    @Override
    public CraterHellion copy() {
        return new CraterHellion(this);
    }

}

class CraterHellionEffect extends OneShotEffect {

    public CraterHellionEffect() {
        super(Outcome.Damage);
        staticText = "it deals 4 damage to each other creature";
    }

    private CraterHellionEffect(final CraterHellionEffect effect) {
        super(effect);
    }

    @Override
    public CraterHellionEffect copy() {
        return new CraterHellionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (!permanent.getId().equals(source.getSourceId())) {
                permanent.damage(4, source.getSourceId(), source, game, false, true);
            }
        }
        return true;
    }
}
