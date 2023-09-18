
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class ExaltedDragon extends CardImpl {

    public ExaltedDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Exalted Dragon can't attack unless you sacrifice a land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ExaltedDragonCostToAttackBlockEffect()));
    }

    private ExaltedDragon(final ExaltedDragon card) {
        super(card);
    }

    @Override
    public ExaltedDragon copy() {
        return new ExaltedDragon(this);
    }
}

class ExaltedDragonCostToAttackBlockEffect extends PayCostToAttackBlockEffectImpl {

    ExaltedDragonCostToAttackBlockEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK,
                new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land"))));
        staticText = "{this} can't attack unless you sacrifice a land. <i>(This cost is paid as attackers are declared.)</i>";
    }

    private ExaltedDragonCostToAttackBlockEffect(final ExaltedDragonCostToAttackBlockEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getSourceId());
    }

    @Override
    public ExaltedDragonCostToAttackBlockEffect copy() {
        return new ExaltedDragonCostToAttackBlockEffect(this);
    }

}
