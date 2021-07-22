package mage.target.common;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ColorAssignment;
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
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class TargetControlledCreatureEachColor extends TargetControlledPermanent {

    private final ColorAssignment colorAssigner;

    private static final FilterControlledPermanent makeFilter(String colors) {
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

    public TargetControlledCreatureEachColor(String colors) {
        super(colors.length(), makeFilter(colors));
        colorAssigner = new ColorAssignment(colors.split(""));
    }

    private TargetControlledCreatureEachColor(final TargetControlledCreatureEachColor target) {
        super(target);
        this.colorAssigner = target.colorAssigner;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(id);
        if (permanent == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(permanent);
        return colorAssigner.getRoleCount(cards, game) >= cards.size();
    }

    @Override
    public TargetControlledCreatureEachColor copy() {
        return new TargetControlledCreatureEachColor(this);
    }
}
