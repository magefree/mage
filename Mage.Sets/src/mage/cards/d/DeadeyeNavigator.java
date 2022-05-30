
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class DeadeyeNavigator extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, each of those creatures has \"{1}{U}: Exile this creature, then return it to the battlefield under your control.\"";

    public DeadeyeNavigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Deadeye Navigator is paired with another creature, each of those creatures has "{1}{U}: Exile this creature, then return it to the battlefield under your control."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileSourceEffect(true), new ManaCostsImpl<>("{1}{U}"));
        ability.addEffect(new ReturnToBattlefieldUnderYourControlSourceEffect());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(ability, ruleText)));
    }

    private DeadeyeNavigator(final DeadeyeNavigator card) {
        super(card);
    }

    @Override
    public DeadeyeNavigator copy() {
        return new DeadeyeNavigator(this);
    }
}
