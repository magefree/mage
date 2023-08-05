package mage.cards.m;

import mage.MageInt;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MerfolkSecretkeeper extends AdventureCard {

    public MerfolkSecretkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{U}", "Venture Deeper", "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Venture Deeper
        // Target player puts the top four cards of their library into their graveyard.
        this.getSpellCard().getSpellAbility().addEffect(new MillCardsTargetEffect(4));
        this.getSpellCard().getSpellAbility().addTarget(new TargetPlayer());
    }

    private MerfolkSecretkeeper(final MerfolkSecretkeeper card) {
        super(card);
    }

    @Override
    public MerfolkSecretkeeper copy() {
        return new MerfolkSecretkeeper(this);
    }
}
