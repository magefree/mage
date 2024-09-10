package mage.cards.u;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;
import mage.target.common.TargetPlaneswalkerPermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrgentNecropsy extends CardImpl {

    public UrgentNecropsy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{G}");

        // As an additional cost to cast this spell, collect evidence X, where X is the total mana value of the permanents this spell targets.
        this.getSpellAbility().addEffect(new InfoEffect(
                "As an additional cost to cast this spell, collect evidence X, " +
                        "where X is the total mana value of the permanents this spell targets."
        ));
        this.getSpellAbility().setCostAdjuster(UrgentNecropsyAdjuster.instance);

        // Destroy up to one target artifact, up to one target creature, up to one target enchantment, and up to one target planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(
                "<br>Destroy up to one target artifact, up to one target creature, " +
                        "up to one target enchantment, and up to one target planeswalker"
        ).setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetArtifactPermanent(0, 1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent(0, 1));
        this.getSpellAbility().addTarget(new TargetPlaneswalkerPermanent(0, 1));
    }

    private UrgentNecropsy(final UrgentNecropsy card) {
        super(card);
    }

    @Override
    public UrgentNecropsy copy() {
        return new UrgentNecropsy(this);
    }
}

enum UrgentNecropsyAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int xValue = ability
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        ability.addCost(new CollectEvidenceCost(xValue));
    }
}
