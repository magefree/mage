
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class WantedScoundrels extends CardImpl {

    public WantedScoundrels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Wanted Scoundrels dies, target opponent creates two colorless Treasure artifact tokens with "T, Sacrifice this artifact: Add one mana of any color."
        Ability ability = new DiesSourceTriggeredAbility(new CreateTokenTargetEffect(new TreasureToken(), 2), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private WantedScoundrels(final WantedScoundrels card) {
        super(card);
    }

    @Override
    public WantedScoundrels copy() {
        return new WantedScoundrels(this);
    }
}
