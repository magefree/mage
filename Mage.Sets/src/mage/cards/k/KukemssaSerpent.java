
package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class KukemssaSerpent extends CardImpl {

    private static final FilterLandPermanent filterOpponentLand = new FilterLandPermanent("land an opponent controls");
    private static final FilterControlledLandPermanent filterControlledLand = new FilterControlledLandPermanent("an Island");

    static {
        filterOpponentLand.add(TargetController.OPPONENT.getControllerPredicate());
        filterControlledLand.add(SubType.ISLAND.getPredicate());
    }

    public KukemssaSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Kukemssa Serpent can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent(SubType.ISLAND, "an Island"))));

        // {U}, Sacrifice an Island: Target land an opponent controls becomes an Island until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesBasicLandTargetEffect(Duration.EndOfTurn, SubType.ISLAND), new ManaCostsImpl<>("{U}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filterControlledLand, true)));
        ability.addTarget(new TargetLandPermanent(filterOpponentLand));
        this.addAbility(ability);

        // When you control no Islands, sacrifice Kukemssa Serpent.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                new FilterLandPermanent(SubType.ISLAND, "no Islands"), ComparisonType.EQUAL_TO, 0,
                new SacrificeSourceEffect()));
    }

    private KukemssaSerpent(final KukemssaSerpent card) {
        super(card);
    }

    @Override
    public KukemssaSerpent copy() {
        return new KukemssaSerpent(this);
    }
}
