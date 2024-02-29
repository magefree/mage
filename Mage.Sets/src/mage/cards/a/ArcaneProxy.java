package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.card.ManaValueLessThanOrEqualToSourcePowerPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcaneProxy extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard(
            "instant or sorcery card with mana value less than or equal to this creature's power"
    );

    static {
        filter.add(ManaValueLessThanOrEqualToSourcePowerPredicate.instance);
    }

    public ArcaneProxy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Prototype {1}{U}{U} -- 2/1
        this.addAbility(new PrototypeAbility(this, "{1}{U}{U}", 2, 1));

        // When Arcane Proxy enters the battlefield, if you cast it, exile target instant or sorcery card with mana value less than or equal to Arcane Proxy's power from your graveyard. Copy that card. You may cast the copy without paying its mana cost.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ExileTargetCardCopyAndCastEffect(true)),
                CastFromEverywhereSourceCondition.instance, "When {this} enters the battlefield, " +
                "if you cast it, exile target instant or sorcery card with mana value less than or equal to {this}'s " +
                "power from your graveyard. Copy that card. You may cast the copy without paying its mana cost."
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private ArcaneProxy(final ArcaneProxy card) {
        super(card);
    }

    @Override
    public ArcaneProxy copy() {
        return new ArcaneProxy(this);
    }
}
