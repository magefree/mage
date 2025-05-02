

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class AngelOfDespair extends CardImpl {

    public AngelOfDespair (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}{B}{B}");
        this.subtype.add(SubType.ANGEL);


        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private AngelOfDespair(final AngelOfDespair card) {
        super(card);
    }

    @Override
    public AngelOfDespair copy() {
        return new AngelOfDespair(this);
    }

}
