
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.AssistAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author TheElk801
 */
public final class VampireCharmseeker extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant, sorcery, or creature card from a graveyard");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY),
                new CardTypePredicate(CardType.CREATURE)
        ));
    }

    public VampireCharmseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Assist
        this.addAbility(new AssistAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Vampire Charmseeker enters the battlefield, return target instant, sorcery, or creature card from a graveyard to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    public VampireCharmseeker(final VampireCharmseeker card) {
        super(card);
    }

    @Override
    public VampireCharmseeker copy() {
        return new VampireCharmseeker(this);
    }
}
