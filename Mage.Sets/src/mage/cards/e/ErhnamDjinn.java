
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ErhnamDjinn extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Wall creature an opponent controls");

    static {
        filter.add(Predicates.not(SubType.WALL.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ErhnamDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.DJINN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, target non-Wall creature an opponent controls gains forestwalk until your next upkeep.
        GainAbilityTargetEffect effect = new GainAbilityTargetEffect(new ForestwalkAbility(false), Duration.UntilYourNextUpkeepStep,
        "target non-Wall creature an opponent controls gains forestwalk until your next upkeep");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private ErhnamDjinn(final ErhnamDjinn card) {
        super(card);
    }

    @Override
    public ErhnamDjinn copy() {
        return new ErhnamDjinn(this);
    }
}
