package mage.cards.b;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;

/**
 *
 * @author muz
 */
public final class BlurOfHeroism extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.HERO, "Hero spells");

    public BlurOfHeroism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When this enchantment enters, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // You may cast Hero spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(
            new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)
        ));
    }

    private BlurOfHeroism(final BlurOfHeroism card) {
        super(card);
    }

    @Override
    public BlurOfHeroism copy() {
        return new BlurOfHeroism(this);
    }
}
