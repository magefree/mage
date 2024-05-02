package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SouredSprings extends CardImpl {

    public SouredSprings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // Soured Springs enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Soured Springs enters the battlefield, it deals 1 damage to target opponent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {T}: Add {U} or {B}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private SouredSprings(final SouredSprings card) {
        super(card);
    }

    @Override
    public SouredSprings copy() {
        return new SouredSprings(this);
    }
}
