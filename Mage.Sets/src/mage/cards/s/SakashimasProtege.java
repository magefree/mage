package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SakashimasProtege extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("permanent that entered the battlefield this turn");

    static {
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public SakashimasProtege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Cascade
        this.addAbility(new CascadeAbility());

        // You may have Sakashima's Protege enter the battlefield as a copy of any permanent that entered the battlefield this turn.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(filter)
                .setText("as a copy of any permanent that entered the battlefield this turn"), true));
    }

    private SakashimasProtege(final SakashimasProtege card) {
        super(card);
    }

    @Override
    public SakashimasProtege copy() {
        return new SakashimasProtege(this);
    }
}
