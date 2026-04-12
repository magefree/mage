package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class AzizaMageTowerCaptain extends CardImpl {

    public AzizaMageTowerCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an instant or sorcery spell, you may tap three untapped creatures you control. If you do, copy that spell. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new DoIfCostPaid(
                new CopyTargetStackObjectEffect(true)
                    .setText("copy that spell. You may choose new targets for the copy"),
                new TapTargetCost(new TargetControlledPermanent(3, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES))
            ), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false, SetTargetPointer.SPELL
        ));

    }

    private AzizaMageTowerCaptain(final AzizaMageTowerCaptain card) {
        super(card);
    }

    @Override
    public AzizaMageTowerCaptain copy() {
        return new AzizaMageTowerCaptain(this);
    }
}
