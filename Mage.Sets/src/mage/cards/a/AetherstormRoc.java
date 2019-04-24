
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AetherstormRoc extends CardImpl {

    private final UUID originalId;

    public AetherstormRoc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Aetherstorm Roc or another creature enters the battlefield under your control, you get {E}.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new GetEnergyCountersControllerEffect(1), new FilterCreaturePermanent("{this} or another creature")));

        // Whenever Aetherstorm Roc attacks, you may pay {E}{E}. If you do, put a +1/+1 counter on it and tap up to one target creature defending player controls.
        DoIfCostPaid doIfCostPaidEffect = new DoIfCostPaid(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new PayEnergyCost(2));
        doIfCostPaidEffect.addEffect(new TapTargetEffect());
        Ability ability = new AttacksTriggeredAbility(doIfCostPaidEffect, false,
                "Whenever {this} attacks you may pay {E}{E}. If you do, put a +1/+1 counter on it and tap up to one target creature defending player controls.");
        ability.addTarget(new TargetCreaturePermanent(0, 1, new FilterCreaturePermanent("creature defending player controls"), false));
        originalId = ability.getOriginalId();
        this.addAbility(ability);

    }

    public AetherstormRoc(final AetherstormRoc card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            ability.getTargets().clear();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
            UUID defenderId = game.getCombat().getDefenderId(ability.getSourceId());
            filter.add(new ControllerIdPredicate(defenderId));
            TargetCreaturePermanent target = new TargetCreaturePermanent(0, 1, filter, false);
            ability.addTarget(target);
        }
    }

    @Override
    public AetherstormRoc copy() {
        return new AetherstormRoc(this);
    }
}
