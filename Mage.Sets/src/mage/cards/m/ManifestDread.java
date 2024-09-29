package mage.cards.m;

import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ManifestDread extends CardImpl {

    public ManifestDread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Manifest dread.
        this.getSpellAbility().addEffect(new ManifestDreadEffect());
    }

    private ManifestDread(final ManifestDread card) {
        super(card);
    }

    @Override
    public ManifestDread copy() {
        return new ManifestDread(this);
    }
}
