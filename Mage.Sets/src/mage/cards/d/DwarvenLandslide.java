
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class DwarvenLandslide extends CardImpl {

    public DwarvenLandslide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Kicker-{2}{R}, Sacrifice a land.
        Costs<Cost> kickerCosts = new CostsImpl<>();
        kickerCosts.add(new ManaCostsImpl<>("{2}{R}"));
        kickerCosts.add(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land"))));
        this.addAbility(new KickerAbility(kickerCosts));
        // Destroy target land. If Dwarven Landslide was kicked, destroy another target land.
        getSpellAbility().addEffect(new DestroyTargetEffect("Destroy target land. if this spell was kicked, destroy another target land"));
        getSpellAbility().addTarget(new TargetLandPermanent());
    }

    public DwarvenLandslide(final DwarvenLandslide card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            if (KickedCondition.instance.apply(game, ability)) {
                ability.getTargets().clear();
                getSpellAbility().addTarget(new TargetLandPermanent(2));
            }
        }
    }

    @Override
    public DwarvenLandslide copy() {
        return new DwarvenLandslide(this);
    }
}
