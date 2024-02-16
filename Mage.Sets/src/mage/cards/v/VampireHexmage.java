
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki, nantuko
 */
public final class VampireHexmage extends CardImpl {

    public VampireHexmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FirstStrikeAbility.getInstance());

        SimpleActivatedAbility vampireHexmageAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VampireHexmageEffect(), new SacrificeSourceCost());
        vampireHexmageAbility.addTarget(new TargetPermanent());
        this.addAbility(vampireHexmageAbility);
    }

    private VampireHexmage(final VampireHexmage card) {
        super(card);
    }

    @Override
    public VampireHexmage copy() {
        return new VampireHexmage(this);
    }
}

class VampireHexmageEffect extends OneShotEffect {

    VampireHexmageEffect() {
        super(Outcome.Benefit);
        staticText = "Remove all counters from target permanent";
    }

    private VampireHexmageEffect(final VampireHexmageEffect effect) {
        super(effect);
    }

    @Override
    public VampireHexmageEffect copy() {
        return new VampireHexmageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            for (Counter counter : permanent.getCounters(game).copy().values()) { // copy to prevent ConcurrentModificationException
                permanent.removeCounters(counter, source, game);
            }
            return true;
        }
        return false;
    }

}
