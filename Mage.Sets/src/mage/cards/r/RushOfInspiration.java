package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RushOfInspiration extends ModalDoubleFacedCard {

    public RushOfInspiration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{1}{U/R}{U/R}",
                "Crackling Falls", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Rush of Inspiration
        // Instant

        // Draw two cards. Then discard a card at random unless you pay {E}{E}.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new DoUnlessControllerPaysEffect(
                        new DiscardControllerEffect(1, true),
                        new PayEnergyCost(2)
                ).setText("Then discard a card at random unless you pay {E}{E}")
        );

        // 2.
        // Crackling Falls
        // Land

        // Crackling Falls enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U} or {R}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());
        this.getRightHalfCard().addAbility(new RedManaAbility());
    }

    private RushOfInspiration(final RushOfInspiration card) {
        super(card);
    }

    @Override
    public RushOfInspiration copy() {
        return new RushOfInspiration(this);
    }
}
