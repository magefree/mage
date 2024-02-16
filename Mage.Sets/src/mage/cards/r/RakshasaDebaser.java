package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.DefendingPlayerOwnsCardPredicate;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RakshasaDebaser extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card from defending player's graveyard");

    static {
        filter.add(DefendingPlayerOwnsCardPredicate.instance);
    }

    private static final String rule
            = "put target creature card from defending player's graveyard onto the battlefield under your control";

    public RakshasaDebaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Rakshasa Debaser attacks, put target creature card from defending player's graveyard onto the battlefield under your control.
        Ability ability = new AttacksTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect().setText(rule), false
        );
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);

        // Encore {6}{B}{B}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{6}{B}{B}")));
    }

    private RakshasaDebaser(final RakshasaDebaser card) {
        super(card);
    }

    @Override
    public RakshasaDebaser copy() {
        return new RakshasaDebaser(this);
    }
}
