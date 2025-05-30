package mage.cards.c;

import mage.MageInt;
import mage.abilities.abilityword.SurvivalAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CautiousSurvivor extends CardImpl {

    public CautiousSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Survival -- At the beginning of your second main phase, if Cautious Survivor is tapped, you gain 2 life.
        this.addAbility(new SurvivalAbility(new GainLifeEffect(2)));
    }

    private CautiousSurvivor(final CautiousSurvivor card) {
        super(card);
    }

    @Override
    public CautiousSurvivor copy() {
        return new CautiousSurvivor(this);
    }
}
