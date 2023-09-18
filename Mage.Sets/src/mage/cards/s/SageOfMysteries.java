package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
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
public final class SageOfMysteries extends CardImpl {

    public SageOfMysteries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Constellation — Whenever an enchantment enters the battlefield under your control, target player puts the top two cards of their library into their graveyard.
        Ability ability = new ConstellationAbility(
                new MillCardsTargetEffect(2), false, false
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private SageOfMysteries(final SageOfMysteries card) {
        super(card);
    }

    @Override
    public SageOfMysteries copy() {
        return new SageOfMysteries(this);
    }
}
