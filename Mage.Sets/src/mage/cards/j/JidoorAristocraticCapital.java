package mage.cards.j;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.MillHalfLibraryTargetEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JidoorAristocraticCapital extends AdventureCard {

    public JidoorAristocraticCapital(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{SubType.TOWN}, "",
                "Overture",
                new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // This land enters tapped.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U}.
        this.getLeftHalfCard().addAbility(new BlueManaAbility());

        // Overture
        // Target opponent mills half their library, rounded down.
        this.getRightHalfCard().getSpellAbility().addEffect(new MillHalfLibraryTargetEffect(false));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetOpponent());

        finalizeCard();
    }

    private JidoorAristocraticCapital(final JidoorAristocraticCapital card) {
        super(card);
    }

    @Override
    public JidoorAristocraticCapital copy() {
        return new JidoorAristocraticCapital(this);
    }
}
