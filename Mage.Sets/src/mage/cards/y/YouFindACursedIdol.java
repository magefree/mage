package mage.cards.y;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YouFindACursedIdol extends CardImpl {

    public YouFindACursedIdol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Choose one —
        // • Smash it — Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().withFirstModeFlavorWord("Smash It");

        // • Life the Curse — Destroy target enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode.withFlavorWord("Lift the Curse"));

        // • Steal Its Eyes — Create an Treasure token and venture into the dungeon.
        mode = new Mode(new CreateTokenEffect(new TreasureToken()));
        mode.addEffect(new VentureIntoTheDungeonEffect().concatBy("and"));
        this.getSpellAbility().addMode(mode.withFlavorWord("Steal Its Eyes"));
    }

    private YouFindACursedIdol(final YouFindACursedIdol card) {
        super(card);
    }

    @Override
    public YouFindACursedIdol copy() {
        return new YouFindACursedIdol(this);
    }
}
