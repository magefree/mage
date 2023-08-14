package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class UmaraWizard extends ModalDoubleFacedCard {

    public UmaraWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.MERFOLK, SubType.WIZARD}, "{4}{U}",
                "Umara Skyfalls", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Umara Wizard
        // Creature — Merfolk Wizard
        this.getLeftHalfCard().setPT(new MageInt(4), new MageInt(3));

        // Whenever you cast an instant, sorcery, or Wizard spell, Umara Wizard gains flying until end of turn.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_INSTANT_SORCERY_WIZARD, false
        ));

        // 2.
        // Umara Skyfalls
        // Land

        // Umara Skyfalls enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());
    }

    private UmaraWizard(final UmaraWizard card) {
        super(card);
    }

    @Override
    public UmaraWizard copy() {
        return new UmaraWizard(this);
    }
}
