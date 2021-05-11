
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class ZadasCommando extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an untapped Ally you control");

    static {
        filter.add(SubType.ALLY.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public ZadasCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.GOBLIN, SubType.ARCHER, SubType.ALLY);
        //this.subtype.add(SubType.GOBLIN);
        //this.subtype.add(SubType.ARCHER);
        //this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: Zada's Commando deals 1 damage to target opponent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        ability.setAbilityWord(AbilityWord.COHORT);
        this.addAbility(ability);
    }

    private ZadasCommando(final ZadasCommando card) {
        super(card);
    }

    @Override
    public ZadasCommando copy() {
        return new ZadasCommando(this);
    }
}
