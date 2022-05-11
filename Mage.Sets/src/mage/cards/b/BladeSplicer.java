package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.PhyrexianGolemToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class BladeSplicer extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("Golems");

    static {
        filter.add(SubType.GOLEM.getPredicate());
    }

    public BladeSplicer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.PHYREXIAN, SubType.HUMAN, SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Blade Splicer enters the battlefield, create a 3/3 colorless Golem artifact creature token.        
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PhyrexianGolemToken())));

        // Golem creatures you control have first strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter)));
    }

    private BladeSplicer(final BladeSplicer card) {
        super(card);
    }

    @Override
    public BladeSplicer copy() {
        return new BladeSplicer(this);
    }
}
