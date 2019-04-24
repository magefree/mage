
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class DesperateCastaways extends CardImpl {

    public DesperateCastaways(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Desperate Castaways can't attack unless you control an artifact.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DesperateCastawaysEffect()));
    }

    public DesperateCastaways(final DesperateCastaways card) {
        super(card);
    }

    @Override
    public DesperateCastaways copy() {
        return new DesperateCastaways(this);
    }
}

class DesperateCastawaysEffect extends RestrictionEffect {

    public DesperateCastawaysEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you control an artifact";
    }

    public DesperateCastawaysEffect(final DesperateCastawaysEffect effect) {
        super(effect);
    }

    @Override
    public DesperateCastawaysEffect copy() {
        return new DesperateCastawaysEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            if (game.getBattlefield().countAll(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, source.getControllerId(), game) > 0) {
                return false;
            }
            return true;
        }  // do not apply to other creatures.
        return false;
    }
}
