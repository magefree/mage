

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public final class BloodhuskRitualist extends CardImpl {

    public BloodhuskRitualist (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.VAMPIRE, SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Multikicker (You may pay an additional {B} any number of times as you cast this spell.)
        this.addAbility(new MultikickerAbility("{B}"));

        // When Bloodhusk Ritualist enters the battlefield, target opponent discards a card for each time it was kicked.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(MultikickerCount.instance));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BloodhuskRitualist(final BloodhuskRitualist card) {
        super(card);
    }

    @Override
    public BloodhuskRitualist copy() {
        return new BloodhuskRitualist(this);
    }

}
