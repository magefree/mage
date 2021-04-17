package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CursedMirror extends CardImpl {

    public CursedMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // As Cursed Mirror enters the battlefield, you may have it become a copy of any creature on the battlefield until end of turn, except it has haste.
        this.addAbility(new EntersBattlefieldAbility(
                new CopyPermanentEffect(new CursedMirrorCopyApplier()).setDuration(Duration.EndOfTurn),
                true, null, "As {this} enters the battlefield, you may have it " +
                "become a copy of any creature on the battlefield until end of turn, except it has haste.", ""
        ));
    }

    private CursedMirror(final CursedMirror card) {
        super(card);
    }

    @Override
    public CursedMirror copy() {
        return new CursedMirror(this);
    }
}

class CursedMirrorCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.getAbilities().add(HasteAbility.getInstance());
        return true;
    }
}
