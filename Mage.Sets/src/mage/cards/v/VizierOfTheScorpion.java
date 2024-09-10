package mage.cards.v;

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
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VizierOfTheScorpion extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ZOMBIE, "Zombie tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public VizierOfTheScorpion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Vizier of the Scorpion enters the battlefield, amass 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AmassEffect(1, SubType.ZOMBIE)));

        // Zombie tokens you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private VizierOfTheScorpion(final VizierOfTheScorpion card) {
        super(card);
    }

    @Override
    public VizierOfTheScorpion copy() {
        return new VizierOfTheScorpion(this);
    }
}
