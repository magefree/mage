
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class Geistblast extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell you control");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public Geistblast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Geistblast deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // {2}{U}, Exile Geistblast from your graveyard: Copy target instant or sorcery you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new CopyTargetSpellEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetSpell(filter));
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);

    }

    private Geistblast(final Geistblast card) {
        super(card);
    }

    @Override
    public Geistblast copy() {
        return new Geistblast(this);
    }
}
