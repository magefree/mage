package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SilverfurPartisan extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("a Wolf or Werewolf you control");

    static {
        filter.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
    }

    public SilverfurPartisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a Wolf or Werewolf you control becomes the target of an instant or sorcery spell, create a 2/2 green Wolf creature token.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(new CreateTokenEffect(new WolfToken()),
                filter, StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY));
    }

    private SilverfurPartisan(final SilverfurPartisan card) {
        super(card);
    }

    @Override
    public SilverfurPartisan copy() {
        return new SilverfurPartisan(this);
    }
}
