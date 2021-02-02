
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Styxo
 */
public final class WompRat extends CardImpl {

    public WompRat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {4}{W}: Monstrosity 1
        this.addAbility(new MonstrosityAbility("{4}{W}", 1));

        // When Womp Rat becomes monstrous you may exilte target card from a graveyard
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(new ExileTargetEffect(), true);
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private WompRat(final WompRat card) {
        super(card);
    }

    @Override
    public WompRat copy() {
        return new WompRat(this);
    }
}
