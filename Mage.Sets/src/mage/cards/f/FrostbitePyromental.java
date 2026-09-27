package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FrostbitePyromental extends CardImpl {

    public FrostbitePyromental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever this creature deals combat damage to a player, draw two cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
            new DrawCardSourceControllerEffect(2), false
        ));

        // At the beginning of the end step, sacrifice this creature.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(TargetController.NEXT, new SacrificeSourceEffect(), false));
    }

    private FrostbitePyromental(final FrostbitePyromental card) {
        super(card);
    }

    @Override
    public FrostbitePyromental copy() {
        return new FrostbitePyromental(this);
    }
}
