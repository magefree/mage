package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class KeldonMarauders extends CardImpl {

    public KeldonMarauders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vanishing 2
        this.addAbility(new VanishingAbility(2));

        // When Keldon Marauders enters the battlefield or leaves the battlefield, it deals 1 damage to target player.
        Ability ability = new EntersBattlefieldOrLeavesSourceTriggeredAbility(new DamageTargetEffect(1, "it"), false);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private KeldonMarauders(final KeldonMarauders card) {
        super(card);
    }

    @Override
    public KeldonMarauders copy() {
        return new KeldonMarauders(this);
    }
}
