package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MetastaticEvangel extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public MetastaticEvangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever another nontoken creature enters the battlefield under your control, proliferate.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ProliferateEffect(), filter));
    }

    private MetastaticEvangel(final MetastaticEvangel card) {
        super(card);
    }

    @Override
    public MetastaticEvangel copy() {
        return new MetastaticEvangel(this);
    }
}
