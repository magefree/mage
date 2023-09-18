
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class MinionOfTeveshSzat extends CardImpl {

    public MinionOfTeveshSzat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, Minion of Tevesh Szat deals 2 damage to you unless you pay {B}{B}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoUnlessControllerPaysEffect(
                        new DamageControllerEffect(2),
                        new ManaCostsImpl<>("{B}{B}")
                ),
                TargetController.YOU, false
        ));

        // {tap}: Target creature gets +3/-2 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(3, -2, Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MinionOfTeveshSzat(final MinionOfTeveshSzat card) {
        super(card);
    }

    @Override
    public MinionOfTeveshSzat copy() {
        return new MinionOfTeveshSzat(this);
    }
}
