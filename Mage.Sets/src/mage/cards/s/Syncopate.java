package mage.cards.s;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Syncopate extends CardImpl {

    public Syncopate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Counter target spell unless its controller pays {X}. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(ManacostVariableValue.REGULAR, true));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Syncopate(final Syncopate card) {
        super(card);
    }

    @Override
    public Syncopate copy() {
        return new Syncopate(this);
    }
}
