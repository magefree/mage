package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UndeadAugur extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.ZOMBIE, "Zombie you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public UndeadAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Undead Augur or another Zombie you control dies, you draw a card and you lose 1 life.
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1).setText("you draw a card"), false, filter
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private UndeadAugur(final UndeadAugur card) {
        super(card);
    }

    @Override
    public UndeadAugur copy() {
        return new UndeadAugur(this);
    }
}
