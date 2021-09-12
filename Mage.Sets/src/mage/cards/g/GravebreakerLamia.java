package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInGraveyardEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.CastFromZonePredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GravebreakerLamia extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells you cast from your graveyard");

    static {
        filter.add(new CastFromZonePredicate(Zone.GRAVEYARD));
    }

    public GravebreakerLamia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.LAMIA);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Gravebreaker Lamia enters the battlefield, search your library for a card, put it into your graveyard, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInGraveyardEffect(), false));

        // Spells you cast from your graveyard cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private GravebreakerLamia(final GravebreakerLamia card) {
        super(card);
    }

    @Override
    public GravebreakerLamia copy() {
        return new GravebreakerLamia(this);
    }
}
