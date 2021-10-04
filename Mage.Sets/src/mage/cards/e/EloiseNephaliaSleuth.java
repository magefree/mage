package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EloiseNephaliaSleuth extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another creature you control");
    private static final FilterPermanent filter2 = new FilterPermanent("a token");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(TokenPredicate.TRUE);
    }

    public EloiseNephaliaSleuth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever another creature you control dies, investigate.
        this.addAbility(new DiesCreatureTriggeredAbility(new InvestigateEffect(1), false, filter));

        // Whenever you sacrifice a token, surveil 1.
        this.addAbility(new SacrificePermanentTriggeredAbility(new SurveilEffect(1), filter2));
    }

    private EloiseNephaliaSleuth(final EloiseNephaliaSleuth card) {
        super(card);
    }

    @Override
    public EloiseNephaliaSleuth copy() {
        return new EloiseNephaliaSleuth(this);
    }
}
