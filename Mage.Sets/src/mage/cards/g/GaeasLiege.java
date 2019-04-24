
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author anonymous
 */
public final class GaeasLiege extends CardImpl {
    
    final static FilterControlledPermanent filterLands = new FilterControlledPermanent("Forests you control");

    static {
        filterLands.add(new SubtypePredicate(SubType.FOREST));
    }

    public GaeasLiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");
        
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As long as Gaea's Liege isn't attacking, its power and toughness are each equal to the number of Forests you control. As long as Gaea's Liege is attacking, its power and toughness are each equal to the number of Forests defending player controls.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ConditionalContinuousEffect(
                new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterLands), Duration.EndOfGame),
                new SetPowerToughnessSourceEffect(new DefendersForestCount(), Duration.EndOfCombat),
                new InvertCondition(SourceAttackingCondition.instance),
                "As long as {this} isn't attacking, its power and toughness are each equal to the number of Forests you control. As long as {this} is attacking, its power and toughness are each equal to the number of Forests defending player controls.")));
        // {tap}: Target land becomes a Forest until Gaea's Liege leaves the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesBasicLandTargetEffect(Duration.WhileOnBattlefield, SubType.FOREST), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    public GaeasLiege(final GaeasLiege card) {
        super(card);
    }

    @Override
    public GaeasLiege copy() {
        return new GaeasLiege(this);
    }
}

class DefendersForestCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (CombatGroup group :game.getCombat().getGroups()) {
            if (group.getAttackers().contains(sourceAbility.getSourceId())) {
                UUID defenderId = group.getDefenderId();
                if (group.isDefenderIsPlaneswalker()) {
                    Permanent permanent = game.getPermanent(defenderId);
                    if (permanent != null) {
                        defenderId = permanent.getControllerId();
                    }
                }
                
                FilterLandPermanent filter = new FilterLandPermanent("forest");
                filter.add(new SubtypePredicate(SubType.FOREST));
                return game.getBattlefield().countAll(filter, defenderId, game);

            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new DefendersForestCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of Forests defending player controls";
    }
}
