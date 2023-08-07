package mage.cards.c;

import mage.MageInt;
import mage.abilities.Pronoun;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConquerorsGalleon extends CardImpl {

    public ConquerorsGalleon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(10);

        this.secondSideCardClazz = mage.cards.c.ConquerorsFoothold.class;

        // When Conqueror's Galleon attacks, exile it at the end of combat, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        this.addAbility(new AttacksTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(
                        new ExileAndReturnSourceEffect(
                                PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.IT, true
                        )
                )), false, "When {this} attacks, exile it at the end of combat, " +
                "then return it to the battlefield transformed under your control."
        ));

        // Crew 4
        this.addAbility(new CrewAbility(4));
    }

    private ConquerorsGalleon(final ConquerorsGalleon card) {
        super(card);
    }

    @Override
    public ConquerorsGalleon copy() {
        return new ConquerorsGalleon(this);
    }
}
