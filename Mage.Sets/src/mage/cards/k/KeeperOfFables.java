package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeeperOfFables extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Human creatures");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public KeeperOfFables(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever one or more non-Human creatures you control deal combat damage to a player, draw a card.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(new DrawCardSourceControllerEffect(1), filter));
    }

    private KeeperOfFables(final KeeperOfFables card) {
        super(card);
    }

    @Override
    public KeeperOfFables copy() {
        return new KeeperOfFables(this);
    }
}