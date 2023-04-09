
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.continuous.SetChosenColorEffect;
import mage.abilities.effects.common.enterAttribute.EnterAttributeSetChosenColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author LoneFox, xenohedron
 */

public final class AlloyGolem extends CardImpl {

    public AlloyGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As Alloy Golem enters the battlefield, choose a color.
        // Alloy Golem is the chosen color.
        Ability ability = new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral));
        ability.addEffect(new EnterAttributeSetChosenColorEffect());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetChosenColorEffect()));
    }

    private AlloyGolem(final AlloyGolem card) {
        super(card);
    }

    @Override
    public AlloyGolem copy() {
        return new AlloyGolem(this);
    }
}
