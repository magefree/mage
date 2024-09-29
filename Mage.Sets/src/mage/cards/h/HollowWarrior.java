
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author L_J
 */
public final class HollowWarrior extends CardImpl {

    public HollowWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.GOLEM);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Hollow Warrior can't attack or block unless you tap an untapped creature you control not declared as an attacking or blocking creature this combat. (This cost is paid as attackers are declared.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HollowWarriorCostToAttackBlockEffect()));

    }
        
    private HollowWarrior(final HollowWarrior card) {
        super(card);
    }

    @Override
    public HollowWarrior copy() {
        return new HollowWarrior(this);
    }
}

class HollowWarriorCostToAttackBlockEffect extends PayCostToAttackBlockEffectImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an untapped creature you control not declared as an attacking or blocking creature");
    static {
        filter.add(Predicates.not(AttackingPredicate.instance));
        filter.add(Predicates.not(BlockingPredicate.instance));
        filter.add(TappedPredicate.UNTAPPED);
    }

    HollowWarriorCostToAttackBlockEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK_AND_BLOCK,
                new TapTargetCost(new TargetControlledPermanent(filter)));
        staticText = "{this} can't attack or block unless you tap an untapped creature you control not declared as an attacking or blocking creature this combat <i>(This cost is paid as attackers are declared.)</i>";
    }

    private HollowWarriorCostToAttackBlockEffect(final HollowWarriorCostToAttackBlockEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getSourceId());
    }

    @Override
    public HollowWarriorCostToAttackBlockEffect copy() {
        return new HollowWarriorCostToAttackBlockEffect(this);
    }

}
