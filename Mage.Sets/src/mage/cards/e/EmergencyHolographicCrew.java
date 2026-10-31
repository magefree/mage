package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author muz
 */
public final class EmergencyHolographicCrew extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.SPACECRAFT);

    public EmergencyHolographicCrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When this creature enters, choose one --
        // * Return target Spacecraft card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));

        // * Surveil 2.
        ability.addMode(new Mode(new SurveilEffect(2)));
        this.addAbility(ability);
    }

    private EmergencyHolographicCrew(final EmergencyHolographicCrew card) {
        super(card);
    }

    @Override
    public EmergencyHolographicCrew copy() {
        return new EmergencyHolographicCrew(this);
    }
}
