package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GothmogMorgulLieutenant extends CardImpl {

    public GothmogMorgulLieutenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Gothmog, Morgul Lieutenant enters the battlefield, amass Orcs 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AmassEffect(1, SubType.ORC)));

        // Creature tokens you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS
        )));
    }

    private GothmogMorgulLieutenant(final GothmogMorgulLieutenant card) {
        super(card);
    }

    @Override
    public GothmogMorgulLieutenant copy() {
        return new GothmogMorgulLieutenant(this);
    }
}
