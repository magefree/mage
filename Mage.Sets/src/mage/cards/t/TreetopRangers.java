
package mage.cards.t;

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
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author Jason E. Wall
 *
 */
public final class TreetopRangers extends CardImpl {

    private static final FilterCreaturePermanent onlyFlyingCreatures = new FilterCreaturePermanent("except by creatures with flying");

    static {
        onlyFlyingCreatures.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public TreetopRangers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Treetop Rangers can't be blocked except by creatures with flying.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(onlyFlyingCreatures, Duration.WhileOnBattlefield)));
    }

    private TreetopRangers(final TreetopRangers card) {
        super(card);
    }

    @Override
    public TreetopRangers copy() {
        return new TreetopRangers(this);
    }
}
