package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.common.FilterPermanentCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AcolyteOfAffliction extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public AcolyteOfAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Acolyte of Affliction enters the battlefield, put the top two cards of your library into your graveyard, then you may return a permanent card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2));
        ability.addEffect(new ReturnCardChosenFromGraveyardEffect(true,
                filter, PutCards.HAND).concatBy(", then"));
        this.addAbility(ability);
    }

    private AcolyteOfAffliction(final AcolyteOfAffliction card) {
        super(card);
    }

    @Override
    public AcolyteOfAffliction copy() {
        return new AcolyteOfAffliction(this);
    }
}
