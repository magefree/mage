package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class GoblinLegionnaire extends CardImpl {

    public GoblinLegionnaire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{W}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}, Sacrifice Goblin Legionnaire: It deals 2 damage to any target.
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2, "it"), new ColoredManaCost(ColoredManaSymbol.R));
        firstAbility.addCost(new SacrificeSourceCost());
        firstAbility.addTarget(new TargetAnyTarget());
        this.addAbility(firstAbility);

        // {W}, Sacrifice Goblin Legionnaire: Prevent the next 2 damage that would be dealt to any target this turn.
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new ColoredManaCost(ColoredManaSymbol.W));
        secondAbility.addCost(new SacrificeSourceCost());
        secondAbility.addTarget(new TargetAnyTarget());
        this.addAbility(secondAbility);
    }

    private GoblinLegionnaire(final GoblinLegionnaire card) {
        super(card);
    }

    @Override
    public GoblinLegionnaire copy() {
        return new GoblinLegionnaire(this);
    }
}
