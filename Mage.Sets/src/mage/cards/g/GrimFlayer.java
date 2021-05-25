package mage.cards.g;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 * @author fireshoes
 */
public final class GrimFlayer extends CardImpl {

    public GrimFlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Grim Flayer deals combat damage to a player, look at the top three cards of your library. Put any number of them into your graveyard
        // and the rest back on top of your library in any order.
        Effect effect = new LookLibraryAndPickControllerEffect(
                StaticValue.get(3), false, StaticValue.get(3), new FilterCard(), Zone.LIBRARY, true, false, true, Zone.GRAVEYARD, false);
        effect.setText("look at the top three cards of your library. Put any number of them into your graveyard and the rest back on top of your library in any order");
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(effect, false));

        // <i>Delirium</i> &mdash; Grim Flayer gets +2/+2 as long as there are four or more card types among cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; {this} gets +2/+2 as long as there are four or more card types among cards in your graveyard"))
                .addHint(CardTypesInGraveyardHint.YOU));
    }

    private GrimFlayer(final GrimFlayer card) {
        super(card);
    }

    @Override
    public GrimFlayer copy() {
        return new GrimFlayer(this);
    }
}
