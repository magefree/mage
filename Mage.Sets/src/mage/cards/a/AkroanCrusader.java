
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.AkroanSoldierToken;

/**
 *
 * @author Plopman
 */
public final class AkroanCrusader extends CardImpl {

    public AkroanCrusader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // <i>Heroic</i>  Whenever you cast a spell that targets Akroan Crusader, create a 1/1 red Soldier creature token with haste.
        this.addAbility(new HeroicAbility(new CreateTokenEffect(new AkroanSoldierToken())));
    }

    private AkroanCrusader(final AkroanCrusader card) {
        super(card);
    }

    @Override
    public AkroanCrusader copy() {
        return new AkroanCrusader(this);
    }
}
