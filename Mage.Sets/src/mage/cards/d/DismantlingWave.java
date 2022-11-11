package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DismantlingWave extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("artifacts and enchantments");

    public DismantlingWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // For each opponent, destroy up to one target artifact or enchantment that player controls.
        this.getSpellAbility().addEffect(new DestroyTargetEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("For each opponent, destroy up to one target artifact or enchantment that player controls."));
        this.getSpellAbility().setTargetAdjuster(DismantlingWaveAdjuster.instance);

        // Cycling {6}{W}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{6}{W}{W}")));

        // When you cycle Dismantling Wave, destroy all artifacts and enchantments.
        this.addAbility(new CycleTriggeredAbility(new DestroyAllEffect(filter)));
    }

    private DismantlingWave(final DismantlingWave card) {
        super(card);
    }

    @Override
    public DismantlingWave copy() {
        return new DismantlingWave(this);
    }
}

enum DismantlingWaveAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        game.getOpponents(ability.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .forEachOrdered(player -> {
                    FilterPermanent filter = new FilterArtifactOrEnchantmentPermanent(
                            "artifact or enchantment controlled by " + player.getName()
                    );
                    filter.add(new ControllerIdPredicate(player.getId()));
                    ability.addTarget(new TargetPermanent(0, 1, filter, false));
                });
    }
}
