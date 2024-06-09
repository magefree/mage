package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaheelisLattice extends CardImpl {

    public SaheelisLattice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");
        this.secondSideCardClazz = mage.cards.m.MastercraftRaptor.class;

        // When Saheeli's Lattice enters the battlefield, you may discard a card. If you do, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(2), new DiscardCardCost())
        ));

        // Craft with one or more Dinosaurs {4}{R}
        this.addAbility(new CraftAbility(
                "{4}{R}", "one or more Dinosaurs", "other Dinosaurs you control " +
                "or Dinosaur cards in your graveyard", 1, Integer.MAX_VALUE, SubType.DINOSAUR.getPredicate()
        ));
    }

    private SaheelisLattice(final SaheelisLattice card) {
        super(card);
    }

    @Override
    public SaheelisLattice copy() {
        return new SaheelisLattice(this);
    }
}
