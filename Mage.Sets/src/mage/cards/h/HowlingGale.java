
package mage.cards.h;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public final class HowlingGale extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public HowlingGale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        // Howling Gale deals 1 damage to each creature with flying and each player.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, filter));
        Effect effect = new DamagePlayersEffect(1);
        effect.setText("and each player");
        this.getSpellAbility().addEffect(effect);
        // Flashback {1}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{G}")));
    }

    private HowlingGale(final HowlingGale card) {
        super(card);
    }

    @Override
    public HowlingGale copy() {
        return new HowlingGale(this);
    }
}
