package mage.cards.h;

import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponent;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HeartlessPillage extends CardImpl {

    public HeartlessPillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent discards two cards.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));

        // Raid — If you attacked with a creature this turn, create a colorless Treasure artifact token with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new TreasureToken()), RaidCondition.instance,
                "<br/><br/><i>Raid</i> &mdash; If you attacked this turn, create a Treasure token"
        ));
        this.getSpellAbility().addWatcher(new PlayerAttackedWatcher());
        this.getSpellAbility().addHint(RaidHint.instance);
    }

    private HeartlessPillage(final HeartlessPillage card) {
        super(card);
    }

    @Override
    public HeartlessPillage copy() {
        return new HeartlessPillage(this);
    }
}
