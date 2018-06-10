
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Wurm1Token;
import mage.game.permanent.token.Wurm2Token;

/**
 *
 * @author Loki
 */
public final class WurmcoilEngine extends CardImpl {

    public WurmcoilEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Deathtouch, lifelink
        this.addAbility(DeathtouchAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());

        // When Wurmcoil Engine dies, create a 3/3 colorless Wurm artifact creature token with deathtouch and a 3/3 colorless Wurm artifact creature token with lifelink.
        Ability ability = new DiesTriggeredAbility(new CreateTokenEffect(new Wurm1Token(expansionSetCode)), false);
        ability.addEffect(new CreateTokenEffect(new Wurm2Token(expansionSetCode)));
        this.addAbility(ability);
    }

    public WurmcoilEngine(final WurmcoilEngine card) {
        super(card);
    }

    @Override
    public WurmcoilEngine copy() {
        return new WurmcoilEngine(this);
    }

}
