package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.game.permanent.token.MercenaryToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HellspurPosseBoss extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("outlaws");

    static {
        filter.add(OutlawPredicate.instance);
    }

    public HellspurPosseBoss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Other outlaws you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));

        // When Hellspur Posse Boss enters the battlefield, create two 1/1 red Mercenary creature tokens with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MercenaryToken(), 2)));
    }

    private HellspurPosseBoss(final HellspurPosseBoss card) {
        super(card);
    }

    @Override
    public HellspurPosseBoss copy() {
        return new HellspurPosseBoss(this);
    }
}
