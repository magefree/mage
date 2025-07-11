package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AllFatesStalker extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Assassin creature");

    static {
        filter.add(Predicates.not(SubType.ASSASSIN.getPredicate()));
    }

    public AllFatesStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.DRIX);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When this creature enters, exile up to one target non-Assassin creature until this creature leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Warp {1}{W}
        this.addAbility(new WarpAbility(this, "{1}{W}"));
    }

    private AllFatesStalker(final AllFatesStalker card) {
        super(card);
    }

    @Override
    public AllFatesStalker copy() {
        return new AllFatesStalker(this);
    }
}
