package mage.cards.l;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightEmUp extends CardImpl {

    public LightEmUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Casualty 2
        this.addAbility(new CasualtyAbility(2));

        // Light 'Em Up deals 2 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private LightEmUp(final LightEmUp card) {
        super(card);
    }

    @Override
    public LightEmUp copy() {
        return new LightEmUp(this);
    }
}
