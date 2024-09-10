package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScrabblingSkullcrab extends CardImpl {

    public ScrabblingSkullcrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, target player mills two cards.
        Ability ability = new EerieAbility(new MillCardsTargetEffect(2));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ScrabblingSkullcrab(final ScrabblingSkullcrab card) {
        super(card);
    }

    @Override
    public ScrabblingSkullcrab copy() {
        return new ScrabblingSkullcrab(this);
    }
}
