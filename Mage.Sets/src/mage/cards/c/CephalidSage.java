
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class CephalidSage extends CardImpl {

    public CephalidSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.CEPHALID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Threshold - As long as seven or more cards are in your graveyard, Cephalid Sage has "When Cephalid Sage enters the battlefield, draw three cards, then discard two cards."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(3, 2))),
            new CardsInControllerGraveyardCondition(7),
            "As long as seven or more cards are in your graveyard, {this} has \"When {this} enters the battlefield, draw three cards, then discard two cards.\""));
            ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private CephalidSage(final CephalidSage card) {
        super(card);
    }

    @Override
    public CephalidSage copy() {
        return new CephalidSage(this);
    }
}
