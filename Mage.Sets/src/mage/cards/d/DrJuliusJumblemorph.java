package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.IsAllCreatureTypesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;

import java.util.UUID;

/**
 * @author vereena42 & L_J
 */
public final class DrJuliusJumblemorph extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a host");

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, -1));
    }

    public DrJuliusJumblemorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Dr. Julius Jumblemorph is every creature type (even if this card isn't on the battlefield).
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new IsAllCreatureTypesSourceEffect()));

        // Whenever a host enters the battlefield under your control, you may search your library and/or graveyard for a card with augment and combine it with that host. If you search your library this way, shuffle it.
        // TODO: Host currently isn't implemented, so this ability currently would never trigger
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new InfoEffect("you may search your library and/or graveyard for a card with augment " +
                        "and combine it with that host. If you search your library this way, shuffle it."), filter
        ));
    }

    private DrJuliusJumblemorph(final DrJuliusJumblemorph card) {
        super(card);
    }

    @Override
    public DrJuliusJumblemorph copy() {
        return new DrJuliusJumblemorph(this);
    }
}
