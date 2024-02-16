package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbyssalGorestalker extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("creatures");

    public AbyssalGorestalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Abyssal Gorestalker enters the battlefield, each player sacrifices two creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeAllEffect(2, filter)));
    }

    private AbyssalGorestalker(final AbyssalGorestalker card) {
        super(card);
    }

    @Override
    public AbyssalGorestalker copy() {
        return new AbyssalGorestalker(this);
    }
}
