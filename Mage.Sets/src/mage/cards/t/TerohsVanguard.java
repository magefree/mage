
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class TerohsVanguard extends CardImpl {

    public TerohsVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Threshold - As long as seven or more cards are in your graveyard, Teroh's Vanguard has "When Teroh's Vanguard enters the battlefield, creatures you control gain protection from black until end of turn."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(new EntersBattlefieldTriggeredAbility(
            new GainAbilityControlledEffect(ProtectionAbility.from(ObjectColor.BLACK), Duration.EndOfTurn, new FilterCreaturePermanent()))),
            new CardsInControllerGraveyardCondition(7),
            "As long as seven or more cards are in your graveyard, {this} has \"When {this} enters the battlefield, creatures you control gain protection from black until end of turn.\""));
            ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);

    }

    private TerohsVanguard(final TerohsVanguard card) {
        super(card);
    }

    @Override
    public TerohsVanguard copy() {
        return new TerohsVanguard(this);
    }
}
