package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoxodonEavesdropper extends CardImpl {

    public LoxodonEavesdropper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Loxodon Eavesdropper enters the battlefield, investigate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvestigateEffect()));

        // Whenever you draw your second card each turn, Loxodon Eavesdropper gets +1/+1 and gains vigilance until end of turn.
        Ability ability = new DrawNthCardTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn)
                        .setText("{this} gets +1/+1"),
                false, 2
        );
        ability.addEffect(new GainAbilitySourceEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains vigilance until end of turn"));
        this.addAbility(ability);
    }

    private LoxodonEavesdropper(final LoxodonEavesdropper card) {
        super(card);
    }

    @Override
    public LoxodonEavesdropper copy() {
        return new LoxodonEavesdropper(this);
    }
}
