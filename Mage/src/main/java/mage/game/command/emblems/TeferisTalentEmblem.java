package mage.game.command.emblems;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.ActivateAbilitiesAnyTimeYouCouldCastInstantEffect;
import mage.constants.Zone;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class TeferisTalentEmblem extends Emblem {
    // -12: "You may activate loyalty abilities of planeswalkers you control on any player's turn any time you could cast an instant."

    public TeferisTalentEmblem() {
        super("Emblem Teferi");
        this.getAbilities().add(new SimpleStaticAbility(
                Zone.COMMAND,
                new ActivateAbilitiesAnyTimeYouCouldCastInstantEffect(
                        LoyaltyAbility.class,
                        "loyalty abilities of planeswalkers you control on any player's turn"
                )
        ));
    }

    private TeferisTalentEmblem(final TeferisTalentEmblem card) {
        super(card);
    }

    @Override
    public TeferisTalentEmblem copy() {
        return new TeferisTalentEmblem(this);
    }
}
