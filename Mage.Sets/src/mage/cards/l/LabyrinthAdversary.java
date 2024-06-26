package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LabyrinthAdversary extends CardImpl {

    public LabyrinthAdversary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you attack, you may pay {1}{R}. When you do, target creature can't block this turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DoWhenCostPaid(ability, new ManaCostsImpl<>("{1}{R}"), "Pay {1}{R}?"), 1
        ));
    }

    private LabyrinthAdversary(final LabyrinthAdversary card) {
        super(card);
    }

    @Override
    public LabyrinthAdversary copy() {
        return new LabyrinthAdversary(this);
    }
}
