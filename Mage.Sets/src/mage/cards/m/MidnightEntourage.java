package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class MidnightEntourage extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.AETHERBORN);

    public MidnightEntourage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Aetherborn you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever Midnight Entourage or another Aetherborn you control dies, you draw a card and you lose 1 life.
        Ability ability = new DiesThisOrAnotherTriggeredAbility(
                new DrawCardSourceControllerEffect(1, true), false, filter
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private MidnightEntourage(final MidnightEntourage card) {
        super(card);
    }

    @Override
    public MidnightEntourage copy() {
        return new MidnightEntourage(this);
    }
}
