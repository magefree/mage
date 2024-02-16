package mage.cards.j;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JayasFirenado extends CardImpl {

    public JayasFirenado(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Jaya's Firenado deals 5 damage to target creature or planeswalker. Scry 1.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new ScryEffect(1));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private JayasFirenado(final JayasFirenado card) {
        super(card);
    }

    @Override
    public JayasFirenado copy() {
        return new JayasFirenado(this);
    }
}
