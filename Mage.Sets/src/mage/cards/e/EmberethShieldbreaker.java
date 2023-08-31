package mage.cards.e;

import mage.MageInt;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmberethShieldbreaker extends AdventureCard {

    public EmberethShieldbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{1}{R}", "Battle Display", "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Battle Display
        // Destroy target artifact.
        this.getSpellCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetArtifactPermanent());

        this.finalizeAdventure();
    }

    private EmberethShieldbreaker(final EmberethShieldbreaker card) {
        super(card);
    }

    @Override
    public EmberethShieldbreaker copy() {
        return new EmberethShieldbreaker(this);
    }
}
