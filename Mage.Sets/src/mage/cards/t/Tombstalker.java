
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.keyword.DelveAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class Tombstalker extends CardImpl {

    public Tombstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Delve
        Ability ability = new DelveAbility();
        ability.setRuleAtTheTop(false);
        this.addAbility(ability);
    }

    private Tombstalker(final Tombstalker card) {
        super(card);
    }

    @Override
    public Tombstalker copy() {
        return new Tombstalker(this);
    }
}
