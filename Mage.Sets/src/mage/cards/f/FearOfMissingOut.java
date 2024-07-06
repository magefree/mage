package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksFirstTimeTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FearOfMissingOut extends CardImpl {

    public FearOfMissingOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Fear of Missing Out enters, discard a card, then draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardControllerEffect(1));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);

        // Delirium -- Whenever Fear of Missing Out attacks for the first time each turn, if there are four or more card types among cards in your graveyard, untap target creature. After this phase, there is an additional combat phase.
        ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksFirstTimeTriggeredAbility(new UntapTargetEffect(), false),
                DeliriumCondition.instance, "<i>Delirium</i> &mdash; Whenever {this} attacks for the first time each turn, "
                + "if there are four or more card types among cards in your graveyard, untap target creature. "
                + "After this phase, there is an additional combat phase."
        );
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new AdditionalCombatPhaseEffect());
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private FearOfMissingOut(final FearOfMissingOut card) {
        super(card);
    }

    @Override
    public FearOfMissingOut copy() {
        return new FearOfMissingOut(this);
    }
}
