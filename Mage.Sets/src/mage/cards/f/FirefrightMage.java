
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByAllTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BursegSardaukar
 */
public final class FirefrightMage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by artifact creatures and/or red creatures");

    static {
        filter.add(Predicates.not(
                Predicates.or(
                        Predicates.and(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()),
                        Predicates.and(CardType.CREATURE.getPredicate(), new ColorPredicate(ObjectColor.RED)
                        ))));
    }

    public FirefrightMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SPELLSHAPER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //{1}{R}, {T}, Discard a card: Target creature can't be blocked this turn except by artifact creatures and/or red creatures.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedByAllTargetEffect(filter, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FirefrightMage(final FirefrightMage card) {
        super(card);
    }

    @Override
    public FirefrightMage copy() {
        return new FirefrightMage(this);
    }
}
