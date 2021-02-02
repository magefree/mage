
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LoneFox
 */
public final class TaoistMystic extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with horsemanship");

    static {
        filter.add(new AbilityPredicate(HorsemanshipAbility.class));
    }

    public TaoistMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MYSTIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Taoist Mystic can't be blocked by creatures with horsemanship.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    private TaoistMystic(final TaoistMystic card) {
        super(card);
    }

    @Override
    public TaoistMystic copy() {
        return new TaoistMystic(this);
    }
}
