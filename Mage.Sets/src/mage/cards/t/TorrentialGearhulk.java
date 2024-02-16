package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MayCastTargetThenExileEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class TorrentialGearhulk extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant card from your graveyard");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public TorrentialGearhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Torrential Gearhulk enters the battlefield, you may cast target 
        // instant card from your graveyard without paying its mana cost.
        // If that card would be put into your graveyard this turn, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MayCastTargetThenExileEffect(true));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private TorrentialGearhulk(final TorrentialGearhulk card) {
        super(card);
    }

    @Override
    public TorrentialGearhulk copy() {
        return new TorrentialGearhulk(this);
    }
}
