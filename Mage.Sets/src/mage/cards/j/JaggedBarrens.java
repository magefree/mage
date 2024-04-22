package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JaggedBarrens extends CardImpl {

    public JaggedBarrens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // Jagged Barrens enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Jagged Barrens enters the battlefield, it deals 1 damage to target opponent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {T}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private JaggedBarrens(final JaggedBarrens card) {
        super(card);
    }

    @Override
    public JaggedBarrens copy() {
        return new JaggedBarrens(this);
    }
}
