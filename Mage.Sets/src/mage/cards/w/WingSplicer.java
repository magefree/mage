package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author North
 */
public final class WingSplicer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Golem creatures");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(SubType.GOLEM.getPredicate());
    }

    public WingSplicer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Wing Splicer enters the battlefield, create a 3/3 colorless Golem artifact creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PhyrexianGolemToken())));

        // Golem creatures you control have flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter)));
    }

    private WingSplicer(final WingSplicer card) {
        super(card);
    }

    @Override
    public WingSplicer copy() {
        return new WingSplicer(this);
    }
}
