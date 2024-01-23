package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteamcoreScholar extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card or a creature card with flying");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                Predicates.and(
                        CardType.CREATURE.getPredicate(),
                        new AbilityPredicate(FlyingAbility.class)
                )
        ));
    }

    public SteamcoreScholar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.WEIRD);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Steamcore Scholar enters the battlefield, draw two cards. Then discard two cards unless you discard an instant or sorcery card or a creature card with flying.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(2));
        ability.addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(2), new DiscardCardCost(filter)
                .setText("discard an instant or sorcery card or a creature card with flying instead of discarding two cards")
        ).setText("Then discard two cards unless you discard an instant or sorcery card or a creature card with flying"));
        this.addAbility(ability);
    }

    private SteamcoreScholar(final SteamcoreScholar card) {
        super(card);
    }

    @Override
    public SteamcoreScholar copy() {
        return new SteamcoreScholar(this);
    }
}
