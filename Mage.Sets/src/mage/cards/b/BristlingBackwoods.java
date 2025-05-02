package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.GreenManaAbility;
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
public final class BristlingBackwoods extends CardImpl {

    public BristlingBackwoods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // Bristling Backwoods enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Bristling Backwoods enters the battlefield, it deals 1 damage to target opponent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private BristlingBackwoods(final BristlingBackwoods card) {
        super(card);
    }

    @Override
    public BristlingBackwoods copy() {
        return new BristlingBackwoods(this);
    }
}
