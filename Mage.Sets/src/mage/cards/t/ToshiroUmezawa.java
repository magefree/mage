package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.MayCastTargetThenExileEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ToshiroUmezawa extends CardImpl {
    private static final FilterCard filter = new FilterCard("instant card from your graveyard");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public ToshiroUmezawa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bushido 1
        this.addAbility(new BushidoAbility(1));
        // Whenever a creature an opponent controls dies, you may cast target instant card from your graveyard. If that card would be put into a graveyard this turn, exile it instead.
        Ability ability = new DiesCreatureTriggeredAbility(new MayCastTargetThenExileEffect(false)
                .setText("you may cast target instant card from your graveyard. "
                        + ThatSpellGraveyardExileReplacementEffect.RULE_A),
                true, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

    }

    private ToshiroUmezawa(final ToshiroUmezawa card) {
        super(card);
    }

    @Override
    public ToshiroUmezawa copy() {
        return new ToshiroUmezawa(this);
    }
}
