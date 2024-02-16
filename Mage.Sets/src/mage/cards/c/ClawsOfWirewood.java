
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public final class ClawsOfWirewood extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public ClawsOfWirewood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // Claws of Wirewood deals 3 damage to each creature with flying and each player.
        this.getSpellAbility().addEffect(new DamageAllEffect(3, filter));
        Effect effect = new DamagePlayersEffect(3);
        effect.setText("and each player");
        this.getSpellAbility().addEffect(effect);
        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private ClawsOfWirewood(final ClawsOfWirewood card) {
        super(card);
    }

    @Override
    public ClawsOfWirewood copy() {
        return new ClawsOfWirewood(this);
    }
}
