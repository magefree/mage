
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author jonubuu
 */
public final class LordOfAtlantis extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Merfolk creatures");

    static {
        filter.add(new SubtypePredicate(SubType.MERFOLK));
    }

    public LordOfAtlantis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Merfolk creatures get +1/+1 and have islandwalk.
        Effect effect = new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true);
        effect.setText("Other Merfolk creatures get +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAllEffect(new IslandwalkAbility(), Duration.WhileOnBattlefield, filter, true);
        effect.setText("and have islandwalk");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public LordOfAtlantis(final LordOfAtlantis card) {
        super(card);
    }

    @Override
    public LordOfAtlantis copy() {
        return new LordOfAtlantis(this);
    }
}
