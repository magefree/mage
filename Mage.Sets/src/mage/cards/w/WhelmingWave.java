
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LevelX2
 */
public final class WhelmingWave extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(Predicates.not(
                Predicates.or(
                        new SubtypePredicate(SubType.KRAKEN),
                        new SubtypePredicate(SubType.LEVIATHAN),
                        new SubtypePredicate(SubType.OCTOPUS),
                        new SubtypePredicate(SubType.SERPENT))));
    }
    
    public WhelmingWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{U}");


        // Return all creatures to their owners' hands except for Krakens, Leviathans, Octopuses and Serpents.
        Effect effect = new ReturnToHandFromBattlefieldAllEffect(filter);
        effect.setText("Return all creatures to their owners' hands except for Krakens, Leviathans, Octopuses and Serpents");
        this.getSpellAbility().addEffect(effect);
    }

    public WhelmingWave(final WhelmingWave card) {
        super(card);
    }

    @Override
    public WhelmingWave copy() {
        return new WhelmingWave(this);
    }
}
