
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.EternalizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class ChampionOfWits extends CardImpl {

    public ChampionOfWits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Champion of Wits enters the battlefield, you may draw cards equal to its power. If you do, discard two cards
        DynamicValue xValue = new SourcePermanentPowerCount();
        Effect effect = new DrawCardSourceControllerEffect(xValue);
        effect.setText("you may draw cards equal to its power");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, true);
        effect = new DiscardControllerEffect(2);
        effect.setText("If you do, discard two cards");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Eternalize {5}{U}{U}
        this.addAbility(new EternalizeAbility(new ManaCostsImpl<>("{5}{U}{U}"), this));
    }

    private ChampionOfWits(final ChampionOfWits card) {
        super(card);
    }

    @Override
    public ChampionOfWits copy() {
        return new ChampionOfWits(this);
    }
}