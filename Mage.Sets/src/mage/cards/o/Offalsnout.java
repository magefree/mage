
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class Offalsnout extends CardImpl {

    public Offalsnout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // When Offalsnout leaves the battlefield, exile target card from a graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect(),false);
        Target target = new TargetCardInGraveyard();
        ability.addTarget(target);
        this.addAbility(ability);
        // Evoke {B}
        this.addAbility(new EvokeAbility(this, "{B}"));
    }

    public Offalsnout(final Offalsnout card) {
        super(card);
    }

    @Override
    public Offalsnout copy() {
        return new Offalsnout(this);
    }
}
