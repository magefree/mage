package mage.cards.t;

import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TransmograntsCrown extends CardImpl {

    public TransmograntsCrown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Whenever equipped creature dies, draw a card.
        this.addAbility(new DiesAttachedTriggeredAbility(
                new DrawCardSourceControllerEffect(1), "equipped creature"
        ));

        // Equip {2} or {B}
        this.addAbility(new EquipAbility(
                Outcome.BoostCreature,
                new OrCost(
                        "{2} or {B}",
                        new GenericManaCost(2),
                        new ManaCostsImpl<>("{B}")
                ), false
        ));
    }

    private TransmograntsCrown(final TransmograntsCrown card) {
        super(card);
    }

    @Override
    public TransmograntsCrown copy() {
        return new TransmograntsCrown(this);
    }
}
