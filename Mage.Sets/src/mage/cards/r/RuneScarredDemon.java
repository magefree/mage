

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public final class RuneScarredDemon extends CardImpl {

    public RuneScarredDemon (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{B}");
        this.subtype.add(SubType.DEMON);


        this.power = new MageInt(6 );
        this.toughness = new MageInt( 6);
        this.addAbility(FlyingAbility.getInstance());
        TargetCardInLibrary target = new TargetCardInLibrary();
        Ability ability = new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(target, false));
        this.addAbility(ability);
    }

    public RuneScarredDemon (final RuneScarredDemon card) {
        super(card);
    }

    @Override
    public RuneScarredDemon copy() {
        return new RuneScarredDemon(this);
    }

}
