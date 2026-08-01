package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.RecruitEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class PatientInstructor extends CardImpl {

    public PatientInstructor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W/U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When this creature enters, recruit.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RecruitEffect()));
    }

    private PatientInstructor(final PatientInstructor card) {
        super(card);
    }

    @Override
    public PatientInstructor copy() {
        return new PatientInstructor(this);
    }
}
