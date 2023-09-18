package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceByPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;

/**
 *
 * @author awjackson
 */
public final class ArmoredTransport extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures blocking it");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
    }

    public ArmoredTransport(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prevent all combat damage that would be dealt to Armored Transport by creatures blocking it.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToSourceByPermanentsEffect(filter, true)));
    }

    private ArmoredTransport(final ArmoredTransport card) {
        super(card);
    }

    @Override
    public ArmoredTransport copy() {
        return new ArmoredTransport(this);
    }
}
