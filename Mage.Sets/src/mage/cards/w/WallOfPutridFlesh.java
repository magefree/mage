package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceByPermanentsEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;

/**
 *
 * @author Galatolol
 */
public final class WallOfPutridFlesh extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("enchanted creatures");

    static {
        filter.add(EnchantedPredicate.instance);
    }

    public WallOfPutridFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));

        // Prevent all damage that would be dealt to Wall of Putrid Flesh by enchanted creatures.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToSourceByPermanentsEffect(filter)));
    }

    private WallOfPutridFlesh(final WallOfPutridFlesh card) {
        super(card);
    }

    @Override
    public WallOfPutridFlesh copy() {
        return new WallOfPutridFlesh(this);
    }
}
