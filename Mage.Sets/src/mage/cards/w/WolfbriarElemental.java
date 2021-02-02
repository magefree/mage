

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WolfToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class WolfbriarElemental extends CardImpl {

    public WolfbriarElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Multikicker (You may pay an additional {G} any number of times as you cast this spell.)
        this.addAbility(new MultikickerAbility("{G}"));

        // When Wolfbriar Elemental enters the battlefield, create a 2/2 green Wolf creature token for each time it was kicked.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WolfToken(), MultikickerCount.instance)));
    }

    private WolfbriarElemental(final WolfbriarElemental card) {
        super(card);
    }

    @Override
    public WolfbriarElemental copy() {
        return new WolfbriarElemental(this);
    }

}
