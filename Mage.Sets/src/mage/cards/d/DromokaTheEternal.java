
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DromokaTheEternal extends CardImpl {

    public DromokaTheEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a Dragon you control attacks, bolster 2.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new BolsterEffect(2), false, new FilterControlledCreaturePermanent(SubType.DRAGON, "Dragon you control")));
    }

    private DromokaTheEternal(final DromokaTheEternal card) {
        super(card);
    }

    @Override
    public DromokaTheEternal copy() {
        return new DromokaTheEternal(this);
    }
}
