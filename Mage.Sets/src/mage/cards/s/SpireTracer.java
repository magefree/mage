
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class SpireTracer extends CardImpl {

    public SpireTracer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Spire Tracer can't be blocked except by creatures with flying or reach.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect()));

    }

    public SpireTracer(final SpireTracer card) {
        super(card);
    }

    @Override
    public SpireTracer copy() {
        return new SpireTracer(this);
    }
}

class CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect extends RestrictionEffect {

    public CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't be blocked except by creatures with flying or reach";
    }

    public CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect(final CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (blocker.getAbilities().containsKey(FlyingAbility.getInstance().getId())
                || blocker.getAbilities().containsKey(ReachAbility.getInstance().getId())) {
            return true;
        }
        return false;
    }

    @Override
    public CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect copy() {
        return new CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect(this);
    }
}
