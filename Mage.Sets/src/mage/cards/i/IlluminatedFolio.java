package mage.cards.i;

import mage.MageItem;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInHand;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class IlluminatedFolio extends CardImpl {

    public IlluminatedFolio(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {1}, {tap}, Reveal two cards from your hand that share a color: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new RevealTargetFromHandCost(new IlluminatedFolioTarget()));
        this.addAbility(ability);
    }

    private IlluminatedFolio(final IlluminatedFolio card) {
        super(card);
    }

    @Override
    public IlluminatedFolio copy() {
        return new IlluminatedFolio(this);
    }
}

class IlluminatedFolioTarget extends TargetCardInHand {

    private static final FilterCard filter = new FilterCard("two cards from your hand that share a color");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
    }

    public IlluminatedFolioTarget() {
        super(2, 2, filter);
    }

    private IlluminatedFolioTarget(final IlluminatedFolioTarget target) {
        super(target);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        return super.canChoose(sourceControllerId, source, game)
                && !possibleTargets(sourceControllerId, source, game).isEmpty();
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        if (this.getTargets().size() == 1) {
            Card card = game.getCard(this.getTargets().get(0));
            possibleTargets.removeIf(
                    uuid -> !game
                            .getCard(uuid)
                            .getColor(game)
                            .shares(card.getColor(game))
            );
            return possibleTargets;
        }
        if (possibleTargets.size() < 2) {
            possibleTargets.clear();
            return possibleTargets;
        }
        Set<Card> allTargets = possibleTargets
                .stream()
                .map(game::getCard)
                .collect(Collectors.toSet());
        possibleTargets.clear();
        for (ObjectColor color : ObjectColor.getAllColors()) {
            Set<Card> inColor = allTargets
                    .stream()
                    .filter(card -> card.getColor(game).shares(color))
                    .collect(Collectors.toSet());
            if (inColor.size() > 1) {
                inColor.stream().map(MageItem::getId).forEach(possibleTargets::add);
            }
            if (possibleTargets.size() == allTargets.size()) {
                break;
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        if (!super.canTarget(id, game)) {
            return false;
        }
        List<UUID> targetList = this.getTargets();
        if (targetList.isEmpty()) {
            return true;
        }
        Card card = game.getCard(id);
        return card != null
                && targetList
                .stream()
                .map(game::getCard)
                .anyMatch(c -> c.getColor(game).shares(card.getColor(game)));
    }

    @Override
    public IlluminatedFolioTarget copy() {
        return new IlluminatedFolioTarget(this);
    }
}
