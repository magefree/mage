package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonBasicLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaelstromArtisan extends PrepareCard {

    public MaelstromArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}", "Rocket Volley", new CardType[]{CardType.SORCERY}, "{1}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Rocket Volley
        // Sorcery {1}{R}
        // Destroy target nonbasic land.
        this.getSpellCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetNonBasicLandPermanent());
    }

    private MaelstromArtisan(final MaelstromArtisan card) {
        super(card);
    }

    @Override
    public MaelstromArtisan copy() {
        return new MaelstromArtisan(this);
    }
}
