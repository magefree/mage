package mage.cards.e;

import java.util.UUID;

import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author muz
 */
public final class ExtendedAbsence extends CardImpl {

    public ExtendedAbsence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Exile target creature or planeswalker. Extended Absence deals 1 damage to each opponent and you gain 1 life.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new DamagePlayersEffect(1, TargetController.OPPONENT));
        this.getSpellAbility().addEffect(new GainLifeEffect(1).concatBy("and"));
    }

    private ExtendedAbsence(final ExtendedAbsence card) {
        super(card);
    }

    @Override
    public ExtendedAbsence copy() {
        return new ExtendedAbsence(this);
    }
}
