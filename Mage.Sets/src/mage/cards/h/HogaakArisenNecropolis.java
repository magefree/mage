package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.MayCastFromGraveyardSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.DelveAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HogaakArisenNecropolis extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, -1));
    }

    public HogaakArisenNecropolis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B/G}{B/G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // You can't spend mana to cast this spell.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("You can't spend mana to cast this spell")
        ));
        this.getSpellAbility().getManaCostsToPay().setSourceFilter(filter);
        this.getSpellAbility().getManaCosts().setSourceFilter(filter);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Delve
        this.addAbility(new DelveAbility());

        // You may cast Hogaak, Arisen Necropolis from your graveyard.
        this.addAbility(new MayCastFromGraveyardSourceAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private HogaakArisenNecropolis(final HogaakArisenNecropolis card) {
        super(card);
    }

    @Override
    public HogaakArisenNecropolis copy() {
        return new HogaakArisenNecropolis(this);
    }
}
