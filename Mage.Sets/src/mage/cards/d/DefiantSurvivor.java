package mage.cards.d;

import mage.MageInt;
import mage.abilities.abilityword.SurvivalAbility;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefiantSurvivor extends CardImpl {

    public DefiantSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Survival -- At the beginning of your second main phase, if Defiant Survivor is tapped, manifest dread.
        this.addAbility(new SurvivalAbility(new ManifestDreadEffect()));
    }

    private DefiantSurvivor(final DefiantSurvivor card) {
        super(card);
    }

    @Override
    public DefiantSurvivor copy() {
        return new DefiantSurvivor(this);
    }
}
