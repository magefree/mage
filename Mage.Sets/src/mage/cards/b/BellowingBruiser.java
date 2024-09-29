package mage.cards.b;

import mage.MageInt;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BellowingBruiser extends AdventureCard {

    public BellowingBruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{4}{R}", "Beat a Path", "{2}{R}");

        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Beat a Path
        // Up to two target creatures can't block this turn.
        this.getSpellCard().getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        this.finalizeAdventure();
    }

    private BellowingBruiser(final BellowingBruiser card) {
        super(card);
    }

    @Override
    public BellowingBruiser copy() {
        return new BellowingBruiser(this);
    }
}
