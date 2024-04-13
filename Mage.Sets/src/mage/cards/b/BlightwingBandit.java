package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.FirstSpellOpponentsTurnTriggeredAbility;
import mage.abilities.effects.common.ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Xanderhall
 */
public final class BlightwingBandit extends CardImpl {

    public BlightwingBandit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        //  Whenever you cast your first spell during each opponent's turn, look at the top card of that player's library, then exile it face down. You may play that card for as long as it remains exiled, and mana of any type can be spent to cast it.
        this.addAbility(new FirstSpellOpponentsTurnTriggeredAbility(
                new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(false, CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE)
                        .setText("look at the top card of that player's library, then exile it face down. "
                                + "You may play that card for as long as it remains exiled, and mana of any type can be spent to cast it."),
                false, SetTargetPointer.PLAYER
        ));
    }

    private BlightwingBandit(final BlightwingBandit card) {
        super(card);
    }

    @Override
    public BlightwingBandit copy() {
        return new BlightwingBandit(this);
    }
}