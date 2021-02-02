
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */
public final class HarbingerOfSpring extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Spirit creatures");
    static {
        filter.add(Predicates.not(SubType.SPIRIT.getPredicate()));
    }

    public HarbingerOfSpring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from non-Spirit creatures
        this.addAbility(new ProtectionAbility(filter));

        // Soulshift 4
        this.addAbility(new SoulshiftAbility(4));
    }

    private HarbingerOfSpring(final HarbingerOfSpring card) {
        super(card);
    }

    @Override
    public HarbingerOfSpring copy() {
        return new HarbingerOfSpring(this);
    }
}
