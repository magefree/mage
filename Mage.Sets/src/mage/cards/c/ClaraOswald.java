package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.CommanderChooseColorAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.AdditionalTriggerObjectReplacementEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClaraOswald extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DOCTOR);

    public ClaraOswald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Impossible Girl -- If Clara Oswald is your commander, choose a color before the game begins. Clara Oswald is the chosen color.
        this.addAbility(new CommanderChooseColorAbility().withFlavorWord("Impossible Girl"));

        // If a triggered ability of a Doctor you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggerObjectReplacementEffect(filter)));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private ClaraOswald(final ClaraOswald card) {
        super(card);
    }

    @Override
    public ClaraOswald copy() {
        return new ClaraOswald(this);
    }
}
