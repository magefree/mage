package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.AndCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttackedThisStepCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.watchers.common.PlayerAttackedStepWatcher;

/**
 *
 * @author Plopman
 */
public final class QasaliAmbusher extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(Predicates.and(SubType.FOREST.getPredicate(), SubType.PLAINS.getPredicate()));
    }

    private static final Condition condition =
            new AndCondition("If a creature is attacking you and you control a Forest and a Plains",
                    AttackedThisStepCondition.instance, new PermanentsOnTheBattlefieldCondition(filter));

    public QasaliAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // If a creature is attacking you and you control a Forest and a Plains,
        // you may cast Qasali Ambusher without paying its mana cost and as though it had flash.
        Ability ability = new AlternativeCostSourceAbility(null, condition);
        ability.addEffect(new ConditionalAsThoughEffect(new CastAsThoughItHadFlashSourceEffect(Duration.EndOfGame), condition)
                .setText("you may cast {this} without paying its mana cost and as though it had flash"));
        ability.addWatcher(new PlayerAttackedStepWatcher());
        this.addAbility(ability);
    }

    public QasaliAmbusher(final QasaliAmbusher card) {
        super(card);
    }

    @Override
    public QasaliAmbusher copy() {
        return new QasaliAmbusher(this);
    }
}