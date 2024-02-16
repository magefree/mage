package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AesiTyrantOfGyreStrait extends CardImpl {

    public AesiTyrantOfGyreStrait(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(
                new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)
        ));

        // Whenever a land enters the battlefield under your control, you may draw a card.
        this.addAbility(new LandfallAbility(new DrawCardSourceControllerEffect(1), true));
    }

    private AesiTyrantOfGyreStrait(final AesiTyrantOfGyreStrait card) {
        super(card);
    }

    @Override
    public AesiTyrantOfGyreStrait copy() {
        return new AesiTyrantOfGyreStrait(this);
    }
}
