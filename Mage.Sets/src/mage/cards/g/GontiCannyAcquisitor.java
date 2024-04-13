package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GontiCannyAcquisitor extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spells you cast but don't own");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public GontiCannyAcquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Spells you cast but don't own cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever one or more creatures you control deal combat damage to a player, look at the top card of that player's library, then exile it face down. You may play that card for as long as it remains exiled, and mana of any type can be spent to cast that spell.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(
                new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(false, CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE)
                        .setText("look at the top card of that player's library, then exile it face down. "
                                + "You may play that card for as long as it remains exiled, and mana of any type can be spent to cast that spell"),
                SetTargetPointer.PLAYER
        ));
    }

    private GontiCannyAcquisitor(final GontiCannyAcquisitor card) {
        super(card);
    }

    @Override
    public GontiCannyAcquisitor copy() {
        return new GontiCannyAcquisitor(this);
    }
}
