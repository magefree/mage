package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttackedThisStepCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.watchers.common.PlayerAttackedStepWatcher;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class QasaliAmbusher extends CardImpl {

    private static final FilterControlledPermanent filterForest = new FilterControlledPermanent(SubType.FOREST);
    private static final FilterControlledPermanent filterPlains = new FilterControlledPermanent(SubType.PLAINS);

    private static final Condition condition = new CompoundCondition(
            "If a creature is attacking you and you control a Forest and a Plains",
            AttackedThisStepCondition.instance,
            new PermanentsOnTheBattlefieldCondition(filterForest),
            new PermanentsOnTheBattlefieldCondition(filterPlains)
    );

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
        Ability ability = new AlternativeCostSourceAbility(
                null, condition, "if a creature is attacking you and you control a Forest and a Plains, " +
                "you may cast {this} without paying its mana cost and as though it had flash."
        );
        ability.addEffect(new ConditionalAsThoughEffect(
                new CastAsThoughItHadFlashSourceEffect(Duration.EndOfGame), condition
        ));
        this.addAbility(ability, new PlayerAttackedStepWatcher());
    }

    private QasaliAmbusher(final QasaliAmbusher card) {
        super(card);
    }

    @Override
    public QasaliAmbusher copy() {
        return new QasaliAmbusher(this);
    }
}
