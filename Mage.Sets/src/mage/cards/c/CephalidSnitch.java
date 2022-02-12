package mage.cards.c;

import java.util.*;
import java.util.List;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author tcontis
 */
public final class CephalidSnitch extends CardImpl {

    public CephalidSnitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.CEPHALID);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Cephalid Snitch: Target creature loses protection from black until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CephalidSnitchEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CephalidSnitch(final CephalidSnitch card) {
        super(card);
    }

    @Override
    public CephalidSnitch copy() {
        return new CephalidSnitch(this);
    }
}
class CephalidSnitchEffect extends LoseAbilityTargetEffect{

    public CephalidSnitchEffect() {
        super(ProtectionAbility.from(ObjectColor.BLACK), Duration.EndOfTurn);
        staticText = "Target creature loses protection from black until end of turn.";
    }

    public CephalidSnitchEffect(final CephalidSnitchEffect effect) {
        super(effect);
    }

    @Override
    public CephalidSnitchEffect copy() {
        return new CephalidSnitchEffect(this);
    }

    private static final ObjectColor NONBLACK = new ObjectColor("WURG");

    private static String filterNameAssembler(List<ObjectColor> colors) {
        if (colors.size() == 1) {
            return colors.get(0).getDescription();
        } else if (colors.size() == 2) {
            return colors.get(0).getDescription() + " and from " + colors.get(1).getDescription();
        } else if (colors.size() == 3) {
            return colors.get(0).getDescription() + ", from " + colors.get(1).getDescription() + " and from " + colors.get(2).getDescription();
        } else if (colors.size() == 4) {
            return colors.get(0).getDescription() + ", from " + colors.get(1).getDescription() + ", from " + colors.get(2).getDescription() + " and from " + colors.get(3).getDescription();
        }
        return "";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
        if (targetCreature != null) {
            List<Ability> toRemove = new ArrayList<>();
            //Go through protection abilities and sort out any containing black
            for (ProtectionAbility a: targetCreature.getAbilities().getProtectionAbilities()) {
                ObjectColor colors = a.getFromColor();
                if (colors.isBlack()) {
                    List<ObjectColor> nonBlackColors = colors.intersection(NONBLACK).getColors();
                    if (!nonBlackColors.isEmpty()) {
                        //If the ability is protection from multiple colors, construct a new filter excluding black
                        FilterCard filter = new FilterCard(filterNameAssembler(nonBlackColors));
                        if (nonBlackColors.size() == 1)
                            filter.add(new ColorPredicate(nonBlackColors.get(0)));
                        else if (nonBlackColors.size() == 2)
                            filter.add(Predicates.or(new ColorPredicate(nonBlackColors.get(0)), new ColorPredicate(nonBlackColors.get(1))));
                        else if (nonBlackColors.size() == 3)
                            filter.add(Predicates.or(new ColorPredicate(nonBlackColors.get(0)), new ColorPredicate(nonBlackColors.get(1)), new ColorPredicate(nonBlackColors.get(2))));
                        else if (nonBlackColors.size() == 4)
                            filter.add(Predicates.or(new ColorPredicate(nonBlackColors.get(0)), new ColorPredicate(nonBlackColors.get(1)), new ColorPredicate(nonBlackColors.get(2)), new ColorPredicate(nonBlackColors.get(3))));
                        a.setFilter(filter);
                    } else {
                        //if the ability is just protection from black, remove it from the creature
                        toRemove.add(a);
                    }
                }
            }
            targetCreature.removeAbilities(toRemove, source.getSourceId(), game);
            return true;
        }
        return false;
    }
}
