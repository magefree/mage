
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LevelX2
 */
public final class SkirkShaman extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by artifact creatures and/or red creatures");

    static {
        filter.add(Predicates.not(
                Predicates.or(
                        Predicates.and(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()),
                        Predicates.and(CardType.CREATURE.getPredicate(), new ColorPredicate(ObjectColor.RED)
                        ))));
    }

    public SkirkShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Skirk Shaman can't be blocked except by artifact creatures and/or red creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

    }

    private SkirkShaman(final SkirkShaman card) {
        super(card);
    }

    @Override
    public SkirkShaman copy() {
        return new SkirkShaman(this);
    }
}
