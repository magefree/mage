
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author Quercitron
 */
public final class GnatAlleyCreeper extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent("creatures with flying");

    static {
        FILTER.add(new AbilityPredicate(FlyingAbility.class));
    }

    public GnatAlleyCreeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Gnat Alley Creeper can't be blocked by creatures with flying.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(FILTER, Duration.WhileOnBattlefield)));
    }

    private GnatAlleyCreeper(final GnatAlleyCreeper card) {
        super(card);
    }

    @Override
    public GnatAlleyCreeper copy() {
        return new GnatAlleyCreeper(this);
    }
}
