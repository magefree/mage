package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class WarFalcon extends CardImpl {

    public WarFalcon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // War Falcon can't attack unless you control a Knight or a Soldier.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WarFalconEffect()));
    }

    private WarFalcon(final WarFalcon card) {
        super(card);
    }

    @Override
    public WarFalcon copy() {
        return new WarFalcon(this);
    }
}

class WarFalconEffect extends RestrictionEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Knight or a Soldier");

    static {
        filter.add(Predicates.or(
                SubType.KNIGHT.getPredicate(),
                SubType.SOLDIER.getPredicate()));
    }

    public WarFalconEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you control a Knight or a Soldier";
    }

    private WarFalconEffect(final WarFalconEffect effect) {
        super(effect);
    }

    @Override
    public WarFalconEffect copy() {
        return new WarFalconEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            return game.getBattlefield().countAll(filter, source.getControllerId(), game) <= 0;
        }  // do not apply to other creatures.
        return false;
    }
}

