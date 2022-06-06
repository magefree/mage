

package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */


public final class HaazdaSnareSquad extends CardImpl {

    public HaazdaSnareSquad (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever Haazda Snare Squad attacks you may pay {W}. If you do, tap target creature an opponent controls.
        Ability ability = new AttacksTriggeredAbility(new DoIfCostPaid(new TapTargetEffect(), new ManaCostsImpl<>("{W}")),false,
                "Whenever {this} attacks, you may pay {W}. If you do, tap target creature an opponent controls.");
        Target target = new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        ability.addTarget(target);
        this.addAbility(ability);

    }

    public HaazdaSnareSquad (final HaazdaSnareSquad card) {
        super(card);
    }

    @Override
    public HaazdaSnareSquad copy() {
        return new HaazdaSnareSquad(this);
    }

}
