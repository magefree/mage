package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BecomesAllBasicsControlledEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DryadOfIlysianGrove extends CardImpl {

    public DryadOfIlysianGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.NYMPH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(
                new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)
        ));

        // Lands you control are every basic land type in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new BecomesAllBasicsControlledEffect()));
    }

    private DryadOfIlysianGrove(final DryadOfIlysianGrove card) {
        super(card);
    }

    @Override
    public DryadOfIlysianGrove copy() {
        return new DryadOfIlysianGrove(this);
    }
}
