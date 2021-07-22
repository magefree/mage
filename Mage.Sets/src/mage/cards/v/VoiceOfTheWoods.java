
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.VoiceOfTheWoodsElementalToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class VoiceOfTheWoods extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Elves you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.ELF.getPredicate());
    }

    public VoiceOfTheWoods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Tap five untapped Elves you control: Create a 7/7 green Elemental creature token with trample.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new VoiceOfTheWoodsElementalToken()),
                new TapTargetCost(new TargetControlledPermanent(5, 5, filter, false)));
        this.addAbility(ability);
    }

    private VoiceOfTheWoods(final VoiceOfTheWoods card) {
        super(card);
    }

    @Override
    public VoiceOfTheWoods copy() {
        return new VoiceOfTheWoods(this);
    }
}
