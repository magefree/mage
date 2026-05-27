package mage.target.common;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.assignment.common.ColorAssignment;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class TargetSacrificeCreatureEachColor extends TargetSacrifice {

    private final ColorAssignment colorAssigner;

    private static FilterControlledPermanent makeFilter(String colors) {
        List<ObjectColor> objectColors
                = Arrays.stream(colors.split(""))
                .map(ObjectColor::new)
                .collect(Collectors.toList());
        FilterControlledPermanent filter
                = new FilterControlledCreaturePermanent(CardUtil.concatWithAnd(
                objectColors
                        .stream()
                        .map(ObjectColor::getDescription)
                        .map(s -> CardUtil.addArticle(s) + " creature")
                        .collect(Collectors.toList())
        ));
        filter.add(Predicates.or(objectColors.stream().map(ColorPredicate::new).collect(Collectors.toList())));
        return filter;
    }

    public TargetSacrificeCreatureEachColor(String colors) {
        super(colors.length(), makeFilter(colors));
        colorAssigner = new ColorAssignment(colors.split(""));
    }

    private TargetSacrificeCreatureEachColor(final TargetSacrificeCreatureEachColor target) {
        super(target);
        this.colorAssigner = target.colorAssigner;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);

        // only valid roles
        Cards existingTargets = new CardsImpl(this.getTargets());
        possibleTargets.removeIf(id -> {
            Permanent permanent = game.getPermanent(id);
            if (permanent == null) {
                return true;
            }
            Cards newTargets = existingTargets.copy();
            newTargets.add(permanent);
            return colorAssigner.hasSharedRoles(newTargets, game);
        });

        return possibleTargets;
    }

    @Override
    public TargetSacrificeCreatureEachColor copy() {
        return new TargetSacrificeCreatureEachColor(this);
    }
}
