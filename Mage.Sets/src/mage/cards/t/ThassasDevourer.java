
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class ThassasDevourer extends CardImpl {

    public ThassasDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Constellation — Whenever Thassa's Devourer or another enchantment enters the battlefield under your control, target player puts the top two cards of their library into their graveyard.
        Ability ability = new ConstellationAbility(new MillCardsTargetEffect(2), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ThassasDevourer(final ThassasDevourer card) {
        super(card);
    }

    @Override
    public ThassasDevourer copy() {
        return new ThassasDevourer(this);
    }
}
