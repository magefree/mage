
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.CentaurToken;
import mage.game.permanent.token.KnightToken;
import mage.game.permanent.token.RhinoToken;

/**
 *
 * @author LevelX2
 */
public final class TrostanisSummoner extends CardImpl {

    public TrostanisSummoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Trostani's Summoner enters the battlefield, create a 2/2 white Knight creature token with vigilance, a 3/3 green Centaur creature token, and a 4/4 green Rhino creature token with trample.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KnightToken()));
        ability.addEffect(new CreateTokenEffect(new CentaurToken()).setText(", a 3/3 green Centaur creature token"));
        ability.addEffect(new CreateTokenEffect(new RhinoToken()).setText(", and a 4/4 green Rhino creature token with trample"));
        this.addAbility(ability);

    }

    private TrostanisSummoner(final TrostanisSummoner card) {
        super(card);
    }

    @Override
    public TrostanisSummoner copy() {
        return new TrostanisSummoner(this);
    }
}
