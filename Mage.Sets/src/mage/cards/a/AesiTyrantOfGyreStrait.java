package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AesiTyrantOfGyreStrait extends CardImpl {

    public AesiTyrantOfGyreStrait(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(
                new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)
        ));

        // Whenever a land enters the battlefield under your control, you may draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT, true
        ));
    }

    private AesiTyrantOfGyreStrait(final AesiTyrantOfGyreStrait card) {
        super(card);
    }

    @Override
    public AesiTyrantOfGyreStrait copy() {
        return new AesiTyrantOfGyreStrait(this);
    }
}
