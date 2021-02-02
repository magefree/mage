
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.EldraziScionToken;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class AbstruseInterference extends CardImpl {

    public AbstruseInterference(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Counter target spell unless its controller pays {1}.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(1)));

        // You create a 1/1 colorless Eldrazi Scion creature token. It has "Sacrifice this creature: Add {C}."
        Effect effect = new CreateTokenEffect(new EldraziScionToken());
        effect.setText("You create a 1/1 colorless Eldrazi Scion creature token. It has \"Sacrifice this creature: Add {C}.\"");
        this.getSpellAbility().addEffect(effect);
    }

    private AbstruseInterference(final AbstruseInterference card) {
        super(card);
    }

    @Override
    public AbstruseInterference copy() {
        return new AbstruseInterference(this);
    }
}
