package mage.cards.a;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.DrawExceptFirstDrawTwoReplacementEffect;
import mage.abilities.effects.common.replacement.GainDoubleLifeReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AlhammarretsArchive extends CardImpl {

    public AlhammarretsArchive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.supertype.add(SuperType.LEGENDARY);

        // If you would gain life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(new GainDoubleLifeReplacementEffect()));

        // If you draw a card except the first one you draw in each of your draw steps, draw two cards instead.
        this.addAbility(new SimpleStaticAbility(new DrawExceptFirstDrawTwoReplacementEffect()), new CardsDrawnDuringDrawStepWatcher());
    }

    private AlhammarretsArchive(final AlhammarretsArchive card) {
        super(card);
    }

    @Override
    public AlhammarretsArchive copy() {
        return new AlhammarretsArchive(this);
    }
}
