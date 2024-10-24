package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class SphinxOfTheChimes extends CardImpl {

    public SphinxOfTheChimes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Discard two nonland cards with the same name: Draw four cards.
        this.addAbility(new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(4), new DiscardTargetCost(new SphinxOfTheChimesTarget())
        ));
    }

    private SphinxOfTheChimes(final SphinxOfTheChimes card) {
        super(card);
    }

    @Override
    public SphinxOfTheChimes copy() {
        return new SphinxOfTheChimes(this);
    }
}

class SphinxOfTheChimesTarget extends TargetCardInHand {

    private static final FilterCard filter = new FilterNonlandCard("nonland cards with the same name");

    public SphinxOfTheChimesTarget() {
        super(2, 2, filter);
    }

    private SphinxOfTheChimesTarget(final SphinxOfTheChimesTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        switch (this.getTargets().size()) {
            case 0:
                if (possibleTargets.size() < 2) {
                    possibleTargets.clear();
                    break;
                }
                Set<UUID> set = CardUtil
                        .streamAllPairwiseMatches(
                                possibleTargets,
                                (u1, u2) -> game.getCard(u1).sharesName(game.getCard(u2), game)
                        )
                        .collect(Collectors.toSet());
                possibleTargets.clear();
                possibleTargets.addAll(set);
                break;
            case 1:
                possibleTargets.removeIf(
                        uuid -> !game
                                .getCard(uuid)
                                .sharesName(game.getCard(this.getTargets().get(0)), game)
                );
                break;
            default:
                possibleTargets.clear();
        }
        return possibleTargets;
    }

    @Override
    public SphinxOfTheChimesTarget copy() {
        return new SphinxOfTheChimesTarget(this);
    }
}
