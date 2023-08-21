package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MayCastTargetThenExileEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class GoblinDarkDwellers extends CardImpl {

    private static final FilterInstantOrSorceryCard filter
            = new FilterInstantOrSorceryCard("instant or sorcery card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public GoblinDarkDwellers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Goblin Dark-Dwellers enters the battlefield, you may cast target instant or sorcery card with converted mana cost 3 or less from your graveyard without paying its mana cost.
        // If that card would be put into your graveyard this turn, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MayCastTargetThenExileEffect(true));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private GoblinDarkDwellers(final GoblinDarkDwellers card) {
        super(card);
    }

    @Override
    public GoblinDarkDwellers copy() {
        return new GoblinDarkDwellers(this);
    }
}
