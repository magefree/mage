
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class GlacialCrasher extends CardImpl {

    public GlacialCrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Glacial Crasher can't attack unless there is a Mountain on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GlacialCrasherEffect()));
    }

    public GlacialCrasher(final GlacialCrasher card) {
        super(card);
    }

    @Override
    public GlacialCrasher copy() {
        return new GlacialCrasher(this);
    }
}

class GlacialCrasherEffect extends RestrictionEffect {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Mountain");

    static {
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
    }

    public GlacialCrasherEffect() {
        super(Duration.WhileOnBattlefield);

        staticText = "{this} can't attack unless there is a Mountain on the battlefield";
    }

    public GlacialCrasherEffect(final GlacialCrasherEffect effect) {
        super(effect);
    }

    @Override
    public GlacialCrasherEffect copy() {
        return new GlacialCrasherEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            if (game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) < 1) {
                return true;
            }
        }
        return false;
    }
}
