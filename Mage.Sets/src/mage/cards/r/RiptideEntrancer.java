package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class RiptideEntrancer extends CardImpl {
    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature that player controls");

    public RiptideEntrancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Riptide Entrancer deals combat damage to a player, you may sacrifice it. If you do, gain control of target creature that player controls.
        Effect effect = new DoIfCostPaid(new GainControlTargetEffect(Duration.WhileOnBattlefield), new SacrificeSourceCost());
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect, false, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        this.addAbility(ability);

        // Morph {U}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{U}{U}")));
    }

    private RiptideEntrancer(final RiptideEntrancer card) {
        super(card);
    }

    @Override
    public RiptideEntrancer copy() {
        return new RiptideEntrancer(this);
    }
}
