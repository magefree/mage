package mage.cards.d;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DemonBolt extends CardImpl {

    public DemonBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Demon Bolt deals 4 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // Foretell {R}
        this.addAbility(new ForetellAbility(this, "{R}"));
    }

    private DemonBolt(final DemonBolt card) {
        super(card);
    }

    @Override
    public DemonBolt copy() {
        return new DemonBolt(this);
    }
}
