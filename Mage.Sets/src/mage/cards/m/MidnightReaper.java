package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author TheElk801
 */
public final class MidnightReaper extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("a nontoken creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(TokenPredicate.FALSE);
    }

    public MidnightReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever a nontoken creature you control dies, Midnight Reaper deals 1 damage to you and you draw a card.
        Ability ability = new DiesCreatureTriggeredAbility(
                new DamageControllerEffect(1), false, filter
        );
        ability.addEffect(
                new DrawCardSourceControllerEffect(1)
                        .setText("and you draw a card")
        );
        this.addAbility(ability);
    }

    private MidnightReaper(final MidnightReaper card) {
        super(card);
    }

    @Override
    public MidnightReaper copy() {
        return new MidnightReaper(this);
    }
}
