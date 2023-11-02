
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class TrialOfSolidarity extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Cartouche");

    static {
        filter.add(SubType.CARTOUCHE.getPredicate());
    }

    public TrialOfSolidarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Trial of Solidarity enters the battlefield, creatures you control get +2/+1 and gain vigilance until end of turn.
        Effect effect = new BoostControlledEffect(2, 1, Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText("creatures you control get +2/+1");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText(" and gain vigilance until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // When a Cartouche enters the battlefield under you control, return Trial of Solidarity to its owner's hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ReturnToHandSourceEffect(), filter
        ));
    }

    private TrialOfSolidarity(final TrialOfSolidarity card) {
        super(card);
    }

    @Override
    public TrialOfSolidarity copy() {
        return new TrialOfSolidarity(this);
    }
}
