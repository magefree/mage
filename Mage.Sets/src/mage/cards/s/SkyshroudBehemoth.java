
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class SkyshroudBehemoth extends CardImpl {

    public SkyshroudBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Fading 2
        this.addAbility(new FadingAbility(2, this));
        // Skyshroud Behemoth enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private SkyshroudBehemoth(final SkyshroudBehemoth card) {
        super(card);
    }

    @Override
    public SkyshroudBehemoth copy() {
        return new SkyshroudBehemoth(this);
    }
}
