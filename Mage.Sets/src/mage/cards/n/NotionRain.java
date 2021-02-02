package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class NotionRain extends CardImpl {

    public NotionRain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{B}");

        // Surveil 2, then draw two cards. Rain of Notions deals 2 damage to you.
        this.getSpellAbility().addEffect(
                new SurveilEffect(2).setText("Surveil 2")
        );
        this.getSpellAbility().addEffect(
                new DrawCardSourceControllerEffect(2)
                        .setText(", then draw two cards")
        );
        this.getSpellAbility().addEffect(new DamageControllerEffect(2));
    }

    private NotionRain(final NotionRain card) {
        super(card);
    }

    @Override
    public NotionRain copy() {
        return new NotionRain(this);
    }
}
