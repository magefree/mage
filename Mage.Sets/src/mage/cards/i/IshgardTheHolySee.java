package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactOrEnchantmentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IshgardTheHolySee extends AdventureCard {

    private static final FilterCard filter = new FilterArtifactOrEnchantmentCard("artifact and/or enchantment cards from your graveyard");

    public IshgardTheHolySee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{SubType.TOWN}, "",
                "Faith & Grief",
                new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // This land enters tapped.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.getLeftHalfCard().addAbility(new WhiteManaAbility());

        // Faith & Grief
        // Return up to two target artifact and/or enchantment cards from your graveyard to your hand.
        this.getRightHalfCard().getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, filter));

        finalizeCard();
    }

    private IshgardTheHolySee(final IshgardTheHolySee card) {
        super(card);
    }

    @Override
    public IshgardTheHolySee copy() {
        return new IshgardTheHolySee(this);
    }
}
