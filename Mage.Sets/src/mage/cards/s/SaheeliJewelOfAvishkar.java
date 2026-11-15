package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class SaheeliJewelOfAvishkar extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.THOPTER, "Thopters");

    public SaheeliJewelOfAvishkar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Thopters you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever you cast a noncreature spell, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new CreateTokenEffect(new ThopterColorlessToken()),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private SaheeliJewelOfAvishkar(final SaheeliJewelOfAvishkar card) {
        super(card);
    }

    @Override
    public SaheeliJewelOfAvishkar copy() {
        return new SaheeliJewelOfAvishkar(this);
    }
}
