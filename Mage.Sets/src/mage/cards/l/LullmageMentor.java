
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCounteredControllerTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.MerfolkToken;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class LullmageMentor extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Merfolk you control");

    static {
        filter.add(SubType.MERFOLK.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public LullmageMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a spell or ability you control counters a spell, you may create a 1/1 blue Merfolk creature token.
        this.addAbility(new SpellCounteredControllerTriggeredAbility(new CreateTokenEffect(new MerfolkToken()), true));

        // Tap seven untapped Merfolk you control: Counter target spell.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(7, 7, filter, true)));
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);

    }

    private LullmageMentor(final LullmageMentor card) {
        super(card);
    }

    @Override
    public LullmageMentor copy() {
        return new LullmageMentor(this);
    }
}
