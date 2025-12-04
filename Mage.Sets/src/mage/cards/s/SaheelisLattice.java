package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaheelisLattice extends TransformingDoubleFacedCard {

    public SaheelisLattice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{R}",
                "Mastercraft Raptor",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.DINOSAUR}, "R"
        );

        // When Saheeli's Lattice enters the battlefield, you may discard a card. If you do, draw two cards.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(2), new DiscardCardCost())
        ));

        // Craft with one or more Dinosaurs {4}{R}
        this.getLeftHalfCard().addAbility(new CraftAbility(
                "{4}{R}", "one or more Dinosaurs", "other Dinosaurs you control " +
                "or Dinosaur cards in your graveyard", 1, Integer.MAX_VALUE, SubType.DINOSAUR.getPredicate()
        ));

        // Mastercraft Raptor
        this.getRightHalfCard().setPT(0, 4);

        // Mastercraft Raptor's power is equal to the total power of the exiled cards used to craft it.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(MastercraftRaptorValue.instance)
                .setText("{this}'s power is equal to the total power of the exiled cards used to craft it")
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

enum MastercraftRaptorValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Card sourceCard = game.getCard(sourceAbility.getSourceId());
        if (sourceCard == null) {
            return 0;
        }
        ExileZone exileZone = game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(
                        game, sourceCard.getMainCard().getId(),
                        sourceCard.getMainCard().getZoneChangeCounter(game) - 1
                ));
        if (exileZone == null) {
            return 0;
        }
        return exileZone
                .getCards(game)
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
    }

    @Override
    public MastercraftRaptorValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
