package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author awjackson
 */
public final class Erithizon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature of defending player's choice");

    public Erithizon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Erithizon attacks, put a +1/+1 counter on target creature of defending player's choice.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.setTargetAdjuster(ErithizonAdjuster.instance);
        this.addAbility(ability);
    }

    private Erithizon(final Erithizon card) {
        super(card);
    }

    @Override
    public Erithizon copy() {
        return new Erithizon(this);
    }
}

enum ErithizonAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        TargetCreaturePermanent target = new TargetCreaturePermanent();
        target.setAbilityController(ability.getControllerId());
        target.setTargetController(game.getCombat().getDefendingPlayerId(ability.getSourceId(), game));
        ability.getTargets().clear();
        ability.getTargets().add(target);
    }
}
