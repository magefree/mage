
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class StensiaBanquet extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Vampires you control");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public StensiaBanquet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Stensia Banquet deals damage to target opponent equal to the number of Vampires you control.
        Effect effect = new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter));
        effect.setText("{this} deals damage to target opponent or planeswalker equal to the number of Vampires you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponentOrPlaneswalker());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private StensiaBanquet(final StensiaBanquet card) {
        super(card);
    }

    @Override
    public StensiaBanquet copy() {
        return new StensiaBanquet(this);
    }
}
