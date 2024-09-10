package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class BlindZealot extends CardImpl {
    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature that player controls");

    public BlindZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN, SubType.HUMAN, SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Intimidate
        this.addAbility(IntimidateAbility.getInstance());

        // Whenever Blind Zealot deals combat damage to a player, you may sacrifice it. If you do, destroy target creature that player controls.
        OneShotEffect effect = new DoIfCostPaid(new DestroyTargetEffect(), new SacrificeSourceCost());
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect, false, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private BlindZealot(final BlindZealot card) {
        super(card);
    }

    @Override
    public BlindZealot copy() {
        return new BlindZealot(this);
    }
}
