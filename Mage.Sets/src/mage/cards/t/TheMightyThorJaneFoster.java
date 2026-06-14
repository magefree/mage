package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

/**
 *
 * @author muz
 */
public final class TheMightyThorJaneFoster extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nontoken artifact or creature");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.EQUIPMENT, "an Equipment");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(
            Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
            )
        );
    }

    public TheMightyThorJaneFoster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever The Mighty Thor attacks, exile up to one target nontoken artifact or creature, then return that card to the battlefield tapped under its owner's control.
        Ability ability = new AttacksTriggeredAbility(
            new ExileThenReturnTargetEffect(false, false, PutCards.BATTLEFIELD_TAPPED)
                .setText("exile up to one target nontoken artifact or creature"
                    + ", then return that card to the battlefield tapped under its owner's control"));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Whenever an Equipment you control enters, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
            new DrawCardSourceControllerEffect(1), filter2
        ));
    }

    private TheMightyThorJaneFoster(final TheMightyThorJaneFoster card) {
        super(card);
    }

    @Override
    public TheMightyThorJaneFoster copy() {
        return new TheMightyThorJaneFoster(this);
    }
}
