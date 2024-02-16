package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CanoptekSpyder extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("another nontoken artifact creature or Vehicle");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                Predicates.and(
                        CardType.ARTIFACT.getPredicate(),
                        CardType.CREATURE.getPredicate()
                ),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public CanoptekSpyder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Fabricator Claw Array -- Whenever another nontoken artifact creature or Vehicle enters the battlefield under your control, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter
        ).withFlavorWord("Fabricator Claw Array"));
    }

    private CanoptekSpyder(final CanoptekSpyder card) {
        super(card);
    }

    @Override
    public CanoptekSpyder copy() {
        return new CanoptekSpyder(this);
    }
}
