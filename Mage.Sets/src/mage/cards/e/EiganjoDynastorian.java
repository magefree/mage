package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class EiganjoDynastorian extends PrepareCard {

    public EiganjoDynastorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}", "Replenish", new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you attack with two or more creatures, this creature becomes prepared.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
            new BecomePreparedSourceEffect(), 2
        ));

        // Replenish
        // Sorcery {3}{W}
        // Return all enchantment cards from your graveyard to the battlefield.
        this.getSpellCard().getSpellAbility().addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(StaticFilters.FILTER_CARD_ENCHANTMENTS));
    }

    private EiganjoDynastorian(final EiganjoDynastorian card) {
        super(card);
    }

    @Override
    public EiganjoDynastorian copy() {
        return new EiganjoDynastorian(this);
    }
}
