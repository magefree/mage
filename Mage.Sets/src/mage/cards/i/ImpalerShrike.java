
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class ImpalerShrike extends CardImpl {

    public ImpalerShrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new SacrificeSourceEffect(), true);
        ability.addEffect(new DrawCardSourceControllerEffect(3));
        this.addAbility(ability);
    }

    private ImpalerShrike(final ImpalerShrike card) {
        super(card);
    }

    @Override
    public ImpalerShrike copy() {
        return new ImpalerShrike(this);
    }
}
