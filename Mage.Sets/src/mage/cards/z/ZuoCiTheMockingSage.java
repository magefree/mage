
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LoneFox
 */
public final class ZuoCiTheMockingSage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with horsemanship");

    static {
        filter.add(new AbilityPredicate(HorsemanshipAbility.class));
    }

    public ZuoCiTheMockingSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // Zuo Ci, the Mocking Sage can't be blocked by creatures with horsemanship.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    private ZuoCiTheMockingSage(final ZuoCiTheMockingSage card) {
        super(card);
    }

    @Override
    public ZuoCiTheMockingSage copy() {
        return new ZuoCiTheMockingSage(this);
    }
}
