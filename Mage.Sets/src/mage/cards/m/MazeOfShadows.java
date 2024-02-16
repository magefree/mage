
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class MazeOfShadows extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature with shadow");

    static {
        filter.add(new AbilityPredicate(ShadowAbility.class));
    }

    public MazeOfShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {tap}: Untap target attacking creature with shadow. Prevent all combat damage that would be dealt to and dealt by that creature this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new TapSourceCost());
        Effect effect = new PreventDamageByTargetEffect(Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage that would be dealt to");
        ability.addEffect(effect);
        effect = new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE, true);
        effect.setText("and dealt by that creature this turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MazeOfShadows(final MazeOfShadows card) {
        super(card);
    }

    @Override
    public MazeOfShadows copy() {
        return new MazeOfShadows(this);
    }
}
