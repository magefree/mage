
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class WindgraceAcolyte extends CardImpl {

    public WindgraceAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Windgrace Acolyte enters the battlefield, put the top three cards of your library into your graveyard and you gain 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3), false);
        ability.addEffect(new GainLifeEffect(3).setText("and you gain 3 life"));
        this.addAbility(ability);
    }

    private WindgraceAcolyte(final WindgraceAcolyte card) {
        super(card);
    }

    @Override
    public WindgraceAcolyte copy() {
        return new WindgraceAcolyte(this);
    }
}
