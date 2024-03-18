package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class GougedZealot extends CardImpl {

    public GougedZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Delirium — Whenever Gouged Zealot attacks, if there are four or more card types among cards in your graveyard, Gouged Zealot deals 1 damage to each creature defending player controls.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new DamageAllControlledTargetEffect(1), false, null, SetTargetPointer.PLAYER),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; Whenever {this} attacks, if there are four or more card types among cards in your graveyard, {this} deals 1 damage to each creature defending player controls."
        ).addHint(CardTypesInGraveyardHint.YOU));
    }

    private GougedZealot(final GougedZealot card) {
        super(card);
    }

    @Override
    public GougedZealot copy() {
        return new GougedZealot(this);
    }
}
