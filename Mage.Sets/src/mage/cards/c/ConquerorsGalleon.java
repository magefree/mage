package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Pronoun;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.*;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConquerorsGalleon extends TransformingDoubleFacedCard {

    public ConquerorsGalleon(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "{4}",
                "Conqueror's Foothold",
                new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );
        this.getLeftHalfCard().setPT(2, 10);

        // When Conqueror's Galleon attacks, exile it at the end of combat, then return it to the battlefield transformed under your control.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(
                        new ExileAndReturnSourceEffect(
                                PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.IT, true
                        )
                )), false, "When {this} attacks, exile it at end of combat, " +
                "then return it to the battlefield transformed under your control."
        ));

        // Crew 4
        this.getLeftHalfCard().addAbility(new CrewAbility(4));

        // Conqueror's Foothold
        // (Transforms from Conqueror's Galleon.)
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new InfoEffect("<i>(Transforms from Conqueror's Galleon.)</i>")));

        // {T}: Add {C}.
        this.getRightHalfCard().addAbility(new ColorlessManaAbility());

        // {2}, {T}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(new DrawDiscardControllerEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability);

        // {4}, {T}: Draw a card.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability);

        // {6}, {T}: Return target card from your graveyard to your hand.
        ability = new SimpleActivatedAbility(new ReturnFromGraveyardToHandTargetEffect(), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard());
        this.getRightHalfCard().addAbility(ability);
    }

    private ConquerorsGalleon(final ConquerorsGalleon card) {
        super(card);
    }

    @Override
    public ConquerorsGalleon copy() {
        return new ConquerorsGalleon(this);
    }
}
